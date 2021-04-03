public class Erreures extends Exception{

    Erreures(String message){
        System.err.println(message);
        System.exit(-1);
    }

}
