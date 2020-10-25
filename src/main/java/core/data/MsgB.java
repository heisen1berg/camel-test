package core.data;
public class MsgB {
    private String txt;
    private String createdDt;
    private double currentTemp;

    public MsgB(){}
    public MsgB(String txt, String createdDt, double currentTemp){
        this.txt=txt;
        this.createdDt=createdDt;
        this.currentTemp=currentTemp;
    }
    public String getTxt() {
        return txt;
    }
    public String getCreatedDt() {
        return createdDt;
    }
    public double getCurrentTemp() {
        return currentTemp;
    }
    @Override
    public String toString(){
        return "{\"txt\":\"" + txt + "\",\"createdDt\":\"" + createdDt + "\",\"currentTemp\":" + currentTemp + "}";
    }
}
