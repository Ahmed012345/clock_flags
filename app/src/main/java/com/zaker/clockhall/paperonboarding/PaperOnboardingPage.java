package com.zaker.clockhall.paperonboarding;

import java.io.Serializable;

/**
 * Represents content for one page of Paper Onboarding
 */
public class PaperOnboardingPage implements Serializable {

    private int titleText;
    private int descriptionText;
    private int bgColor;
    private int contentIconRes;
    private int bottomBarIconRes;

    public PaperOnboardingPage() {
    }

    public PaperOnboardingPage(int titleText, int descriptionText, int bgColor, int contentIconRes, int bottomBarIconRes) {
        this.bgColor = bgColor;
        this.contentIconRes = contentIconRes;
        this.bottomBarIconRes = bottomBarIconRes;
        this.descriptionText = descriptionText;
        this.titleText = titleText;
    }

    public int getTitleText() {
        return titleText;
    }

    public void setTitleText(int titleText) {
        this.titleText = titleText;
    }

    public int getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(int descriptionText) {
        this.descriptionText = descriptionText;
    }

    public int getContentIconRes() {
        return contentIconRes;
    }

    public void setContentIconRes(int contentIconRes) {
        this.contentIconRes = contentIconRes;
    }

    public int getBottomBarIconRes() {
        return bottomBarIconRes;
    }

    public void setBottomBarIconRes(int bottomBarIconRes) {
        this.bottomBarIconRes = bottomBarIconRes;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaperOnboardingPage that = (PaperOnboardingPage) o;

        if (bgColor != that.bgColor) return false;
        if (contentIconRes != that.contentIconRes) return false;
        if (bottomBarIconRes != that.bottomBarIconRes) return false;
        if (titleText != that.titleText) return false;
        return descriptionText != that.descriptionText;

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + titleText;
        result = 31 * result + descriptionText;
        result = 31 * result + bgColor;
        result = 31 * result + contentIconRes;
        result = 31 * result + bottomBarIconRes;
        return result;
    }

    @Override
    public String toString() {
        return "PaperOnboardingPage{" +
                "titleText='" + titleText + '\'' +
                ", descriptionText='" + descriptionText + '\'' +
                ", bgColor=" + bgColor +
                ", contentIconRes=" + contentIconRes +
                ", bottomBarIconRes=" + bottomBarIconRes +
                '}';
    }
}
