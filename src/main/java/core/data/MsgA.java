package core.data;

import java.util.HashMap;
import java.util.Map;

public class MsgA {
    private String msg;
    private String lng;
    private Map<String,String> coordinates;

    public MsgA(String msg,String lng,String latitude,String longtitude){
        this.msg=msg;
        this.lng=lng;
        coordinates=new HashMap<String,String>();
        coordinates.put("latitude",latitude);
        coordinates.put("longtitude",longtitude);
    }
    public MsgA(){}

    public String getMsg() {
        return msg;
    }
    public String getLng() {
        return lng;
    }
    public Map<String, String> getCoordinates() {
        return coordinates;
    }
    public String extractLatitude() {
        return coordinates.get("latitude");
    }
    public String extractLongtitude() {
        return coordinates.get("longtitude");
    }

    @Override
    public String toString(){
        return "{\"msg\":\"" + msg + "\",\"lng\":\"" + lng + "\",\"latitude\":\"" + extractLatitude() + "\",\"longtitude\":\"" + extractLongtitude() + "\"}";
    }

}
