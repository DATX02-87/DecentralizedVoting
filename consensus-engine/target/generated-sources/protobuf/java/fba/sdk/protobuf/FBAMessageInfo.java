// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: fba_message.proto

package fba.sdk.protobuf;

/**
 * <pre>
 * Represents all common information used in a FBA message
 * </pre>
 *
 * Protobuf type {@code FBAMessageInfo}
 */
public  final class FBAMessageInfo extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:FBAMessageInfo)
    FBAMessageInfoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FBAMessageInfo.newBuilder() to construct.
  private FBAMessageInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FBAMessageInfo() {
    msgType_ = "";
    seqNum_ = 0L;
    signerId_ = com.google.protobuf.ByteString.EMPTY;
    unlId_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private FBAMessageInfo(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            msgType_ = s;
            break;
          }
          case 16: {

            seqNum_ = input.readUInt64();
            break;
          }
          case 26: {

            signerId_ = input.readBytes();
            break;
          }
          case 34: {
            if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
              unlId_ = new java.util.ArrayList<com.google.protobuf.ByteString>();
              mutable_bitField0_ |= 0x00000008;
            }
            unlId_.add(input.readBytes());
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
        unlId_ = java.util.Collections.unmodifiableList(unlId_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return fba.sdk.protobuf.FbaMessage.internal_static_FBAMessageInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return fba.sdk.protobuf.FbaMessage.internal_static_FBAMessageInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            fba.sdk.protobuf.FBAMessageInfo.class, fba.sdk.protobuf.FBAMessageInfo.Builder.class);
  }

  private int bitField0_;
  public static final int MSG_TYPE_FIELD_NUMBER = 1;
  private volatile java.lang.Object msgType_;
  /**
   * <pre>
   * Type of the message
   * </pre>
   *
   * <code>string msg_type = 1;</code>
   */
  public java.lang.String getMsgType() {
    java.lang.Object ref = msgType_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      msgType_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Type of the message
   * </pre>
   *
   * <code>string msg_type = 1;</code>
   */
  public com.google.protobuf.ByteString
      getMsgTypeBytes() {
    java.lang.Object ref = msgType_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      msgType_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SEQ_NUM_FIELD_NUMBER = 2;
  private long seqNum_;
  /**
   * <pre>
   * Sequence number
   * </pre>
   *
   * <code>uint64 seq_num = 2;</code>
   */
  public long getSeqNum() {
    return seqNum_;
  }

  public static final int SIGNER_ID_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString signerId_;
  /**
   * <pre>
   * Node who signed the message
   * </pre>
   *
   * <code>bytes signer_id = 3;</code>
   */
  public com.google.protobuf.ByteString getSignerId() {
    return signerId_;
  }

  public static final int UNL_ID_FIELD_NUMBER = 4;
  private java.util.List<com.google.protobuf.ByteString> unlId_;
  /**
   * <pre>
   * List of all UNL that this is meant for
   * </pre>
   *
   * <code>repeated bytes unl_id = 4;</code>
   */
  public java.util.List<com.google.protobuf.ByteString>
      getUnlIdList() {
    return unlId_;
  }
  /**
   * <pre>
   * List of all UNL that this is meant for
   * </pre>
   *
   * <code>repeated bytes unl_id = 4;</code>
   */
  public int getUnlIdCount() {
    return unlId_.size();
  }
  /**
   * <pre>
   * List of all UNL that this is meant for
   * </pre>
   *
   * <code>repeated bytes unl_id = 4;</code>
   */
  public com.google.protobuf.ByteString getUnlId(int index) {
    return unlId_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getMsgTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, msgType_);
    }
    if (seqNum_ != 0L) {
      output.writeUInt64(2, seqNum_);
    }
    if (!signerId_.isEmpty()) {
      output.writeBytes(3, signerId_);
    }
    for (int i = 0; i < unlId_.size(); i++) {
      output.writeBytes(4, unlId_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getMsgTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, msgType_);
    }
    if (seqNum_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(2, seqNum_);
    }
    if (!signerId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, signerId_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < unlId_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeBytesSizeNoTag(unlId_.get(i));
      }
      size += dataSize;
      size += 1 * getUnlIdList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof fba.sdk.protobuf.FBAMessageInfo)) {
      return super.equals(obj);
    }
    fba.sdk.protobuf.FBAMessageInfo other = (fba.sdk.protobuf.FBAMessageInfo) obj;

    boolean result = true;
    result = result && getMsgType()
        .equals(other.getMsgType());
    result = result && (getSeqNum()
        == other.getSeqNum());
    result = result && getSignerId()
        .equals(other.getSignerId());
    result = result && getUnlIdList()
        .equals(other.getUnlIdList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + MSG_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getMsgType().hashCode();
    hash = (37 * hash) + SEQ_NUM_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getSeqNum());
    hash = (37 * hash) + SIGNER_ID_FIELD_NUMBER;
    hash = (53 * hash) + getSignerId().hashCode();
    if (getUnlIdCount() > 0) {
      hash = (37 * hash) + UNL_ID_FIELD_NUMBER;
      hash = (53 * hash) + getUnlIdList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBAMessageInfo parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(fba.sdk.protobuf.FBAMessageInfo prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Represents all common information used in a FBA message
   * </pre>
   *
   * Protobuf type {@code FBAMessageInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:FBAMessageInfo)
      fba.sdk.protobuf.FBAMessageInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBAMessageInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBAMessageInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              fba.sdk.protobuf.FBAMessageInfo.class, fba.sdk.protobuf.FBAMessageInfo.Builder.class);
    }

    // Construct using fba.sdk.protobuf.FBAMessageInfo.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      msgType_ = "";

      seqNum_ = 0L;

      signerId_ = com.google.protobuf.ByteString.EMPTY;

      unlId_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000008);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBAMessageInfo_descriptor;
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBAMessageInfo getDefaultInstanceForType() {
      return fba.sdk.protobuf.FBAMessageInfo.getDefaultInstance();
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBAMessageInfo build() {
      fba.sdk.protobuf.FBAMessageInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBAMessageInfo buildPartial() {
      fba.sdk.protobuf.FBAMessageInfo result = new fba.sdk.protobuf.FBAMessageInfo(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.msgType_ = msgType_;
      result.seqNum_ = seqNum_;
      result.signerId_ = signerId_;
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        unlId_ = java.util.Collections.unmodifiableList(unlId_);
        bitField0_ = (bitField0_ & ~0x00000008);
      }
      result.unlId_ = unlId_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof fba.sdk.protobuf.FBAMessageInfo) {
        return mergeFrom((fba.sdk.protobuf.FBAMessageInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(fba.sdk.protobuf.FBAMessageInfo other) {
      if (other == fba.sdk.protobuf.FBAMessageInfo.getDefaultInstance()) return this;
      if (!other.getMsgType().isEmpty()) {
        msgType_ = other.msgType_;
        onChanged();
      }
      if (other.getSeqNum() != 0L) {
        setSeqNum(other.getSeqNum());
      }
      if (other.getSignerId() != com.google.protobuf.ByteString.EMPTY) {
        setSignerId(other.getSignerId());
      }
      if (!other.unlId_.isEmpty()) {
        if (unlId_.isEmpty()) {
          unlId_ = other.unlId_;
          bitField0_ = (bitField0_ & ~0x00000008);
        } else {
          ensureUnlIdIsMutable();
          unlId_.addAll(other.unlId_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      fba.sdk.protobuf.FBAMessageInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (fba.sdk.protobuf.FBAMessageInfo) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object msgType_ = "";
    /**
     * <pre>
     * Type of the message
     * </pre>
     *
     * <code>string msg_type = 1;</code>
     */
    public java.lang.String getMsgType() {
      java.lang.Object ref = msgType_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        msgType_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Type of the message
     * </pre>
     *
     * <code>string msg_type = 1;</code>
     */
    public com.google.protobuf.ByteString
        getMsgTypeBytes() {
      java.lang.Object ref = msgType_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        msgType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Type of the message
     * </pre>
     *
     * <code>string msg_type = 1;</code>
     */
    public Builder setMsgType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      msgType_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Type of the message
     * </pre>
     *
     * <code>string msg_type = 1;</code>
     */
    public Builder clearMsgType() {
      
      msgType_ = getDefaultInstance().getMsgType();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Type of the message
     * </pre>
     *
     * <code>string msg_type = 1;</code>
     */
    public Builder setMsgTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      msgType_ = value;
      onChanged();
      return this;
    }

    private long seqNum_ ;
    /**
     * <pre>
     * Sequence number
     * </pre>
     *
     * <code>uint64 seq_num = 2;</code>
     */
    public long getSeqNum() {
      return seqNum_;
    }
    /**
     * <pre>
     * Sequence number
     * </pre>
     *
     * <code>uint64 seq_num = 2;</code>
     */
    public Builder setSeqNum(long value) {
      
      seqNum_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Sequence number
     * </pre>
     *
     * <code>uint64 seq_num = 2;</code>
     */
    public Builder clearSeqNum() {
      
      seqNum_ = 0L;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString signerId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Node who signed the message
     * </pre>
     *
     * <code>bytes signer_id = 3;</code>
     */
    public com.google.protobuf.ByteString getSignerId() {
      return signerId_;
    }
    /**
     * <pre>
     * Node who signed the message
     * </pre>
     *
     * <code>bytes signer_id = 3;</code>
     */
    public Builder setSignerId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      signerId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Node who signed the message
     * </pre>
     *
     * <code>bytes signer_id = 3;</code>
     */
    public Builder clearSignerId() {
      
      signerId_ = getDefaultInstance().getSignerId();
      onChanged();
      return this;
    }

    private java.util.List<com.google.protobuf.ByteString> unlId_ = java.util.Collections.emptyList();
    private void ensureUnlIdIsMutable() {
      if (!((bitField0_ & 0x00000008) == 0x00000008)) {
        unlId_ = new java.util.ArrayList<com.google.protobuf.ByteString>(unlId_);
        bitField0_ |= 0x00000008;
       }
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public java.util.List<com.google.protobuf.ByteString>
        getUnlIdList() {
      return java.util.Collections.unmodifiableList(unlId_);
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public int getUnlIdCount() {
      return unlId_.size();
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public com.google.protobuf.ByteString getUnlId(int index) {
      return unlId_.get(index);
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public Builder setUnlId(
        int index, com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureUnlIdIsMutable();
      unlId_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public Builder addUnlId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureUnlIdIsMutable();
      unlId_.add(value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public Builder addAllUnlId(
        java.lang.Iterable<? extends com.google.protobuf.ByteString> values) {
      ensureUnlIdIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, unlId_);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of all UNL that this is meant for
     * </pre>
     *
     * <code>repeated bytes unl_id = 4;</code>
     */
    public Builder clearUnlId() {
      unlId_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:FBAMessageInfo)
  }

  // @@protoc_insertion_point(class_scope:FBAMessageInfo)
  private static final fba.sdk.protobuf.FBAMessageInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new fba.sdk.protobuf.FBAMessageInfo();
  }

  public static fba.sdk.protobuf.FBAMessageInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FBAMessageInfo>
      PARSER = new com.google.protobuf.AbstractParser<FBAMessageInfo>() {
    @java.lang.Override
    public FBAMessageInfo parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new FBAMessageInfo(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FBAMessageInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<FBAMessageInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public fba.sdk.protobuf.FBAMessageInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

