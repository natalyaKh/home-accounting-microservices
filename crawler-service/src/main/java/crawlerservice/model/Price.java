package crawlerservice.model;


public class Price {
    private Integer rub;
    private Integer cop;
    private String val;

    public Price() {
    }

    public Integer getRub() {
        return rub;
    }

    public void setRub(Integer rub) {
        this.rub = rub;
    }

    public Integer getCop() {
        return cop;
    }

    public void setCop(Integer cop) {
        this.cop = cop;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Price(Integer rub, Integer cop, String val) {
        this.rub = rub;
        this.cop = cop;
        this.val = val;
    }
}
