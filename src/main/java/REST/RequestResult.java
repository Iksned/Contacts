package REST;

public class RequestResult {
    String result;
    Object resultObj;

    public RequestResult(String result, Object resultObj) {
        this.result = result;
        this.resultObj = resultObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }
}
