// Generated by the protocol buffer compiler.  DO NOT EDIT!

package zgl.com.cn.model_hotel.hotel_bean;

import zgl.com.cn.libandroid.net.network.bean.ParcelableMessageNano;
import zgl.com.cn.libandroid.net.network.bean.ParcelableMessageNanoCreator;

@SuppressWarnings("hiding")
public final class MoHotelRequestBase extends
        ParcelableMessageNano {

  // Used by Parcelable
  @SuppressWarnings({"unused"})
  public static final android.os.Parcelable.Creator<MoHotelRequestBase> CREATOR =
      new ParcelableMessageNanoCreator<
                MoHotelRequestBase>(MoHotelRequestBase.class);

  private static volatile MoHotelRequestBase[] _emptyArray;
  public static MoHotelRequestBase[] emptyArray() {
    // Lazily initializes the empty array
    if (_emptyArray == null) {
      synchronized (
          com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
        if (_emptyArray == null) {
          _emptyArray = new MoHotelRequestBase[0];
        }
      }
    }
    return _emptyArray;
  }

  // optional string AppVersion = 1;
  public String appVersion;

  // optional string SDKVersion = 2;
  public String sDKVersion;

  // optional .SourceWay SourceWay = 3 [default = SourceWayNoSetting];
  public int sourceWay;

  // optional .LanguageVersion ClientLanguage = 4 [default = CN];
  public int clientLanguage;

  // optional .AppSource AppSource = 5 [default = NoSetting];
  public int appSource;

  // optional int32 FunctionVersion = 6 [default = 0];
  public int functionVersion;

  // optional int32 CustomerId = 7 [default = 0];
  public int customerId;

  public MoHotelRequestBase() {
    clear();
  }

  public MoHotelRequestBase clear() {
    appVersion = "";
    sDKVersion = "";
    sourceWay = 40;
    clientLanguage = 0;
    appSource = 0;
    functionVersion = 0;
    customerId = 0;
    cachedSize = -1;
    return this;
  }

  @Override
  public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
      throws java.io.IOException {
    if (!this.appVersion.equals("")) {
      output.writeString(1, this.appVersion);
    }
    if (!this.sDKVersion.equals("")) {
      output.writeString(2, this.sDKVersion);
    }
    if (this.sourceWay != 0) {
      output.writeInt32(3, this.sourceWay);
    }
    if (this.clientLanguage != 0) {
      output.writeInt32(4, this.clientLanguage);
    }
    if (this.appSource != 40) {
      output.writeInt32(5, this.appSource);
    }
    if (this.functionVersion != 0) {
      output.writeInt32(6, this.functionVersion);
    }
    if (this.customerId != 0) {
      output.writeInt32(7, this.customerId);
    }
    super.writeTo(output);
  }

  @Override
  protected int computeSerializedSize() {
    int size = super.computeSerializedSize();
    if (!this.appVersion.equals("")) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(1, this.appVersion);
    }
    if (!this.sDKVersion.equals("")) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(2, this.sDKVersion);
    }
    if (this.sourceWay != 0) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeInt32Size(3, this.sourceWay);
    }
    if (this.clientLanguage != 0) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeInt32Size(4, this.clientLanguage);
    }
    if (this.appSource != 40) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeInt32Size(5, this.appSource);
    }
    if (this.functionVersion != 0) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt32Size(6, this.functionVersion);
    }
    if (this.customerId != 0) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt32Size(7, this.customerId);
    }
    return size;
  }

  @Override
  public MoHotelRequestBase mergeFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    while (true) {
      int tag = input.readTag();
      switch (tag) {
        case 0:
          return this;
        default: {
          if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
            return this;
          }
          break;
        }
        case 10: {
          this.appVersion = input.readString();
          break;
        }
        case 18: {
          this.sDKVersion = input.readString();
          break;
        }
        case 24: {
          int value = input.readInt32();
          this.sourceWay = value;
          break;
        }
        case 32: {
          int value = input.readInt32();
          this.clientLanguage = value;
          break;
        }
        case 40: {
          int value = input.readInt32();
          this.appSource = value;
          break;
        }
        case 48: {
          this.functionVersion = input.readInt32();
          break;
        }
        case 56: {
          this.customerId = input.readInt32();
          break;
        }
      }
    }
  }

  public static MoHotelRequestBase parseFrom(byte[] data)
      throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
    return mergeFrom(new MoHotelRequestBase(), data);
  }

  public static MoHotelRequestBase parseFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    return new MoHotelRequestBase().mergeFrom(input);
  }
}
