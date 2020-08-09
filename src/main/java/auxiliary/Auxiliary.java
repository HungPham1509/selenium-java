package auxiliary;

public class Auxiliary {
    public int delayBetween(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
    public Auxiliary(){}
}
