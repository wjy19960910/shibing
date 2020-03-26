package intern_server.shibing.data.vo;

/**
 * @Author: wangjingyuan
 * @Date: 2020/3/24 14:50
 */
public class DealVo {
    private String dealDate;
    private String dealName;
    private String reportName;
    private String dealRemark;

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDealRemark() {
        return dealRemark;
    }

    public void setDealRemark(String dealRemark) {
        this.dealRemark = dealRemark;
    }
}
