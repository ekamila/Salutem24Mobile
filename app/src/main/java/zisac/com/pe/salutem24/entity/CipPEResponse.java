package zisac.com.pe.salutem24.entity;

public class CipPEResponse {
    private boolean IsSuccess;
    private int code;
    private String message;
    private CipPEData data;
    public CipPEResponse()
    {
        data = new CipPEData();
    }
}
