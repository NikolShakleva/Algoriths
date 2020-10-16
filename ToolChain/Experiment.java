public class Experiment {
    
    public static void main(String[] args) {
    String [] modes = new String [] {"sorted", "backwards", "same", "random", "semi-sorted"};
    for (int i =0; i< modes.length; i++) {
        String  [] array = new String [] {modes[i], "10", "1234"};
        Producer.main(array);
        
    }
    }
}
