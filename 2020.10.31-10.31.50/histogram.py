import pandas as pd
import pathlib
import math
from scipy.stats import norm
import matplotlib.pyplot as plt
import numpy as np

# Handle path to file ##################################
dir = str(pathlib.Path().absolute())+'/'
dir_file = dir + '2020.10.31-10.31.50/'

# Read Data ############################################
data1 = pd.read_csv(dir_file + 'M64_HyperLogLog.csv',   sep = ";")
data2 = pd.read_csv(dir_file + 'M128_HyperLogLog.csv',  sep = ";")
data3 = pd.read_csv(dir_file + 'M256_HyperLogLog.csv',  sep = ";")
data4 = pd.read_csv(dir_file + 'M512_HyperLogLog.csv',  sep = ";")
data5 = pd.read_csv(dir_file + 'M1024_HyperLogLog.csv', sep = ";")

all_files = [data1, data2, data2, data4, data5]
for i in range(len(all_files)):
    all_files[i] = pd.DataFrame(all_files[i])

# Prepare data #########################################

def prepare(df):
    m = df['M'].max()               # size for m
    sigma = 1.04/math.sqrt(m)       # sigma σ = 1.04/√m.
    n = df['N'].max()               # size of n
    mu = df['Output'].mean()        # mean
    std = mu * sigma                # standard divation mean * σ

    std1 = [mu+std, mu-std]         # standard diviation σ1
    std2 = [mu+std*2, mu-std*2]     # standard diviation σ2

    # dropping data not in σ1 or σ2
    index = df[(df['Output'] > std2[0])|(df['Output'] < std2[1])].index
    df.drop(index, inplace = True)
    
    div = n/10                      # finding number to divide data with
    d = df['Output'].to_numpy()     # changing data frame to numpy array
    d = d/div                       # dividing data to 2 numbers
    std1 = [i/div for i in std1]    # dividing data to 2 decimal
    std2= [i/div for i in std2]     # dividing data to 2 decimal

    make_hist(d, mu, std1, std2,m, n)  


# Histogram method #########################################

def make_hist(d, mu, std1, std2, m, n):
    mu, std = norm.fit(d)       # Fit a normal distribution to the data:

    # Plot the histogram.
    plt.hist(d, bins=15, density=True, alpha=0.6, color='steelblue')
    y_pos = 0.025

    # Plot the PDF.
    xmin, xmax = plt.xlim()
    x = np.linspace(xmin, xmax, 100)
    p = norm.pdf(x, mu, std)

    plt.plot(x, p, 'mediumspringgreen', linewidth=2)
    # axes for standard divation
    plt.axvline(std1[0], color='dimgrey', linestyle='dashed', linewidth=1)
    plt.axvline(std1[1], color='dimgrey', linestyle='dashed', linewidth=1)
    plt.axvline(std2[0], color='dimgrey', linestyle='dashed', linewidth=1)
    plt.axvline(std2[1], color='dimgrey', linestyle='dashed', linewidth=1)
    plt.axvline(mu,      color='dimgrey', linestyle='dashed', linewidth=1)
    # text for standard deviation
    plt.text((mu + ((std1[0]- mu)/3)), y_pos, "1σ")
    plt.text((std1[1] + ((mu - std1[1])/3)), y_pos, "1σ")
    plt.text((std1[0] + ((std2[0]- std1[0])/3)), y_pos, "2σ")
    plt.text((std2[1] + ((std1[1] - std2[1])/3)), y_pos, "2σ")

    title = "HyperLogLog N: " + str(n) + " M: " + str(m) + "\nFit results: mean: %.2f,  std: %.2f" % (mu, std)
    plt.title(title)
    plt.savefig( dir +'/Fig/HyperLogLog_M' + str(m))
    plt.show()

# Make Histogram #########################################

for i in all_files:             # Printing all histograms
    prepare(i)

