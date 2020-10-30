import pandas as pd
import pathlib
import math
from scipy.stats import norm
import matplotlib.pyplot as plt
import numpy as np

s = str(pathlib.Path().absolute()) + '/2020.10.30-20.08.09/M64_HyperLogLog.csv'
data = pd.read_csv(s, sep = ";")
new_data = pd.DataFrame(data)
print(new_data.describe())
m = new_data['M'].max()
sigma = 1.04/math.sqrt(m)
n = new_data['N'].max()
mu = new_data['Output'].mean()
std = mu * sigma
high = mu + std*2
low = mu - std*2

index = new_data[(new_data['Output'] > high)|(new_data['Output'] < low)].index
new_data.drop(index, inplace = True)
print(new_data.describe())

d = new_data['Output'].to_numpy()
print(d)

# Fit a normal distribution to the data:
mu, std = norm.fit(d)

# Plot the histogram.
plt.hist(d, bins=15, density=True, alpha=0.6, color='g')

# Plot the PDF.
xmin, xmax = plt.xlim()
x = np.linspace(xmin, xmax, 100)
p = norm.pdf(x, mu, std)
plt.plot(x, p, 'k', linewidth=2)
title = "Fit results: mu = %.2f,  std = %.2f" % (mu, std)
plt.title(title)

plt.show()


