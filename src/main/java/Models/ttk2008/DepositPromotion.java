package Models.ttk2008;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class DepositPromotion {
    private int promotionID;
    private BigDecimal minAmount;
    private BigDecimal maxAmount; // nullable
    private BigDecimal discountPercent; // percent e.g. 10.00 => 10%
    private Timestamp startDate;
    private Timestamp endDate;
    private int statusID;

    // STATUS INFO (from join)
    private String statusCode;
    private String statusName;

    // getters / setters
    public int getPromotionID() { return promotionID; }
    public void setPromotionID(int promotionID) { this.promotionID = promotionID; }

    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }

    public BigDecimal getMaxAmount() { return maxAmount; }
    public void setMaxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; }

    public BigDecimal getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(BigDecimal discountPercent) { this.discountPercent = discountPercent; }

    public Timestamp getStartDate() { return startDate; }
    public void setStartDate(Timestamp startDate) { this.startDate = startDate; }

    public Timestamp getEndDate() { return endDate; }
    public void setEndDate(Timestamp endDate) { this.endDate = endDate; }

    public int getStatusID() { return statusID; }
    public void setStatusID(int statusID) { this.statusID = statusID; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
}
