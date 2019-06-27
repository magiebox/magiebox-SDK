package com.tszj.chain.sdk.security;


import java.io.Serializable;

public final class RestResponse<T> implements Serializable {
  private int code;
  private String msg;
  private T data;

  private RestResponse(Builder<T> builder) {
    this.code = builder.code;
    this.msg = builder.msg;
    this.data = builder.data;
  }

  public static RestResponse OK(String msg ,Object data) {
    return new Builder().setMsg(msg).setData(data).build();
  }

  public static RestResponse OK(Object data) {
    return new Builder().setData(data).build();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder<T> {
    private int code;
    private String msg;
    private T data;

    public Builder setCode(int code) {
      this.code = code;
      return this;
    }

    public Builder setMsg(String msg) {
      this.msg = msg;
      return this;
    }

    public Builder setData(T data) {
      this.data = data;
      return this;
    }

    public RestResponse build() {
      int nullCode = 0;
      this.code = nullCode == this.code ? 200 : this.code;

      return new RestResponse(this);
    }
  }
  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public T getData() {
    return data;
  }
}
