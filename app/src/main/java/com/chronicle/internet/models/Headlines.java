
package com.chronicle.internet.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Headlines {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("totalResults", totalResults).append("articles", articles).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(articles).append(totalResults).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Headlines) == false) {
            return false;
        }
        Headlines rhs = ((Headlines) other);
        return new EqualsBuilder().append(articles, rhs.articles).append(totalResults, rhs.totalResults).append(status, rhs.status).isEquals();
    }

}
