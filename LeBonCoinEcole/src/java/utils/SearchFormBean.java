package utils;

public class SearchFormBean {

    private String school;
    private String areaCode;
    private String key;
    private int minPrice;
    private int maxPrice;
    private int page;

    public SearchFormBean() {
        page = 1;
        school = new String();
        areaCode = new String();
        key = new String();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = (school != null) ? school : "";
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = (areaCode != null) ? areaCode : "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = (key != null) ? key : "";
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = (minPrice >= 0) ? minPrice : 0;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = (maxPrice >= 0) ? maxPrice : 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page >= 1) ? page : 1;
    }
}
