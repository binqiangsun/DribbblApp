package com.dribbb.sun.model.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunbinqiang on 19/11/2016.
 */

public class LikeResponse implements Parcelable {
    private int id;
    private String created_at;

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.created_at);
    }

    public LikeResponse() {
    }

    protected LikeResponse(Parcel in) {
        this.id = in.readInt();
        this.created_at = in.readString();
    }

    public static final Parcelable.Creator<LikeResponse> CREATOR = new Parcelable.Creator<LikeResponse>() {
        @Override
        public LikeResponse createFromParcel(Parcel source) {
            return new LikeResponse(source);
        }

        @Override
        public LikeResponse[] newArray(int size) {
            return new LikeResponse[size];
        }
    };
}
