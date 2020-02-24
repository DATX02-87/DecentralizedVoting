// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consensus.proto

package sawtooth.sdk.protobuf;

/**
 * Protobuf type {@code ConsensusBlocksGetResponse}
 */
public  final class ConsensusBlocksGetResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ConsensusBlocksGetResponse)
    ConsensusBlocksGetResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConsensusBlocksGetResponse.newBuilder() to construct.
  private ConsensusBlocksGetResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConsensusBlocksGetResponse() {
    status_ = 0;
    blocks_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ConsensusBlocksGetResponse(
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
          case 8: {
            int rawValue = input.readEnum();

            status_ = rawValue;
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              blocks_ = new java.util.ArrayList<sawtooth.sdk.protobuf.ConsensusBlock>();
              mutable_bitField0_ |= 0x00000002;
            }
            blocks_.add(
                input.readMessage(sawtooth.sdk.protobuf.ConsensusBlock.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        blocks_ = java.util.Collections.unmodifiableList(blocks_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusBlocksGetResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusBlocksGetResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.class, sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Builder.class);
  }

  /**
   * Protobuf enum {@code ConsensusBlocksGetResponse.Status}
   */
  public enum Status
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>STATUS_UNSET = 0;</code>
     */
    STATUS_UNSET(0),
    /**
     * <code>OK = 1;</code>
     */
    OK(1),
    /**
     * <code>BAD_REQUEST = 2;</code>
     */
    BAD_REQUEST(2),
    /**
     * <code>SERVICE_ERROR = 3;</code>
     */
    SERVICE_ERROR(3),
    /**
     * <code>NOT_READY = 4;</code>
     */
    NOT_READY(4),
    /**
     * <code>UNKNOWN_BLOCK = 5;</code>
     */
    UNKNOWN_BLOCK(5),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>STATUS_UNSET = 0;</code>
     */
    public static final int STATUS_UNSET_VALUE = 0;
    /**
     * <code>OK = 1;</code>
     */
    public static final int OK_VALUE = 1;
    /**
     * <code>BAD_REQUEST = 2;</code>
     */
    public static final int BAD_REQUEST_VALUE = 2;
    /**
     * <code>SERVICE_ERROR = 3;</code>
     */
    public static final int SERVICE_ERROR_VALUE = 3;
    /**
     * <code>NOT_READY = 4;</code>
     */
    public static final int NOT_READY_VALUE = 4;
    /**
     * <code>UNKNOWN_BLOCK = 5;</code>
     */
    public static final int UNKNOWN_BLOCK_VALUE = 5;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Status valueOf(int value) {
      return forNumber(value);
    }

    public static Status forNumber(int value) {
      switch (value) {
        case 0: return STATUS_UNSET;
        case 1: return OK;
        case 2: return BAD_REQUEST;
        case 3: return SERVICE_ERROR;
        case 4: return NOT_READY;
        case 5: return UNKNOWN_BLOCK;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Status>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Status> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Status>() {
            public Status findValueByNumber(int number) {
              return Status.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.getDescriptor().getEnumTypes().get(0);
    }

    private static final Status[] VALUES = values();

    public static Status valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private Status(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ConsensusBlocksGetResponse.Status)
  }

  private int bitField0_;
  public static final int STATUS_FIELD_NUMBER = 1;
  private int status_;
  /**
   * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
   */
  public int getStatusValue() {
    return status_;
  }
  /**
   * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status getStatus() {
    @SuppressWarnings("deprecation")
    sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status result = sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.valueOf(status_);
    return result == null ? sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.UNRECOGNIZED : result;
  }

  public static final int BLOCKS_FIELD_NUMBER = 2;
  private java.util.List<sawtooth.sdk.protobuf.ConsensusBlock> blocks_;
  /**
   * <code>repeated .ConsensusBlock blocks = 2;</code>
   */
  public java.util.List<sawtooth.sdk.protobuf.ConsensusBlock> getBlocksList() {
    return blocks_;
  }
  /**
   * <code>repeated .ConsensusBlock blocks = 2;</code>
   */
  public java.util.List<? extends sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> 
      getBlocksOrBuilderList() {
    return blocks_;
  }
  /**
   * <code>repeated .ConsensusBlock blocks = 2;</code>
   */
  public int getBlocksCount() {
    return blocks_.size();
  }
  /**
   * <code>repeated .ConsensusBlock blocks = 2;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusBlock getBlocks(int index) {
    return blocks_.get(index);
  }
  /**
   * <code>repeated .ConsensusBlock blocks = 2;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusBlockOrBuilder getBlocksOrBuilder(
      int index) {
    return blocks_.get(index);
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
    if (status_ != sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.STATUS_UNSET.getNumber()) {
      output.writeEnum(1, status_);
    }
    for (int i = 0; i < blocks_.size(); i++) {
      output.writeMessage(2, blocks_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.STATUS_UNSET.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, status_);
    }
    for (int i = 0; i < blocks_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, blocks_.get(i));
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
    if (!(obj instanceof sawtooth.sdk.protobuf.ConsensusBlocksGetResponse)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.ConsensusBlocksGetResponse other = (sawtooth.sdk.protobuf.ConsensusBlocksGetResponse) obj;

    boolean result = true;
    result = result && status_ == other.status_;
    result = result && getBlocksList()
        .equals(other.getBlocksList());
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
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + status_;
    if (getBlocksCount() > 0) {
      hash = (37 * hash) + BLOCKS_FIELD_NUMBER;
      hash = (53 * hash) + getBlocksList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parseFrom(
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
  public static Builder newBuilder(sawtooth.sdk.protobuf.ConsensusBlocksGetResponse prototype) {
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
   * Protobuf type {@code ConsensusBlocksGetResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ConsensusBlocksGetResponse)
      sawtooth.sdk.protobuf.ConsensusBlocksGetResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusBlocksGetResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusBlocksGetResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.class, sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.newBuilder()
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
        getBlocksFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      status_ = 0;

      if (blocksBuilder_ == null) {
        blocks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        blocksBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusBlocksGetResponse_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse build() {
      sawtooth.sdk.protobuf.ConsensusBlocksGetResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse buildPartial() {
      sawtooth.sdk.protobuf.ConsensusBlocksGetResponse result = new sawtooth.sdk.protobuf.ConsensusBlocksGetResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.status_ = status_;
      if (blocksBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          blocks_ = java.util.Collections.unmodifiableList(blocks_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.blocks_ = blocks_;
      } else {
        result.blocks_ = blocksBuilder_.build();
      }
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
      if (other instanceof sawtooth.sdk.protobuf.ConsensusBlocksGetResponse) {
        return mergeFrom((sawtooth.sdk.protobuf.ConsensusBlocksGetResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.ConsensusBlocksGetResponse other) {
      if (other == sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.getDefaultInstance()) return this;
      if (other.status_ != 0) {
        setStatusValue(other.getStatusValue());
      }
      if (blocksBuilder_ == null) {
        if (!other.blocks_.isEmpty()) {
          if (blocks_.isEmpty()) {
            blocks_ = other.blocks_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureBlocksIsMutable();
            blocks_.addAll(other.blocks_);
          }
          onChanged();
        }
      } else {
        if (!other.blocks_.isEmpty()) {
          if (blocksBuilder_.isEmpty()) {
            blocksBuilder_.dispose();
            blocksBuilder_ = null;
            blocks_ = other.blocks_;
            bitField0_ = (bitField0_ & ~0x00000002);
            blocksBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBlocksFieldBuilder() : null;
          } else {
            blocksBuilder_.addAllMessages(other.blocks_);
          }
        }
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
      sawtooth.sdk.protobuf.ConsensusBlocksGetResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.ConsensusBlocksGetResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int status_ = 0;
    /**
     * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
     */
    public int getStatusValue() {
      return status_;
    }
    /**
     * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
     */
    public Builder setStatusValue(int value) {
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status getStatus() {
      @SuppressWarnings("deprecation")
      sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status result = sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.valueOf(status_);
      return result == null ? sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status.UNRECOGNIZED : result;
    }
    /**
     * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
     */
    public Builder setStatus(sawtooth.sdk.protobuf.ConsensusBlocksGetResponse.Status value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      status_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.ConsensusBlocksGetResponse.Status status = 1;</code>
     */
    public Builder clearStatus() {
      
      status_ = 0;
      onChanged();
      return this;
    }

    private java.util.List<sawtooth.sdk.protobuf.ConsensusBlock> blocks_ =
      java.util.Collections.emptyList();
    private void ensureBlocksIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        blocks_ = new java.util.ArrayList<sawtooth.sdk.protobuf.ConsensusBlock>(blocks_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> blocksBuilder_;

    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.ConsensusBlock> getBlocksList() {
      if (blocksBuilder_ == null) {
        return java.util.Collections.unmodifiableList(blocks_);
      } else {
        return blocksBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public int getBlocksCount() {
      if (blocksBuilder_ == null) {
        return blocks_.size();
      } else {
        return blocksBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock getBlocks(int index) {
      if (blocksBuilder_ == null) {
        return blocks_.get(index);
      } else {
        return blocksBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder setBlocks(
        int index, sawtooth.sdk.protobuf.ConsensusBlock value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.set(index, value);
        onChanged();
      } else {
        blocksBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder setBlocks(
        int index, sawtooth.sdk.protobuf.ConsensusBlock.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.set(index, builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder addBlocks(sawtooth.sdk.protobuf.ConsensusBlock value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.add(value);
        onChanged();
      } else {
        blocksBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder addBlocks(
        int index, sawtooth.sdk.protobuf.ConsensusBlock value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.add(index, value);
        onChanged();
      } else {
        blocksBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder addBlocks(
        sawtooth.sdk.protobuf.ConsensusBlock.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.add(builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder addBlocks(
        int index, sawtooth.sdk.protobuf.ConsensusBlock.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.add(index, builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder addAllBlocks(
        java.lang.Iterable<? extends sawtooth.sdk.protobuf.ConsensusBlock> values) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, blocks_);
        onChanged();
      } else {
        blocksBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder clearBlocks() {
      if (blocksBuilder_ == null) {
        blocks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        blocksBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public Builder removeBlocks(int index) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.remove(index);
        onChanged();
      } else {
        blocksBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock.Builder getBlocksBuilder(
        int index) {
      return getBlocksFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlockOrBuilder getBlocksOrBuilder(
        int index) {
      if (blocksBuilder_ == null) {
        return blocks_.get(index);  } else {
        return blocksBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public java.util.List<? extends sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> 
         getBlocksOrBuilderList() {
      if (blocksBuilder_ != null) {
        return blocksBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(blocks_);
      }
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock.Builder addBlocksBuilder() {
      return getBlocksFieldBuilder().addBuilder(
          sawtooth.sdk.protobuf.ConsensusBlock.getDefaultInstance());
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock.Builder addBlocksBuilder(
        int index) {
      return getBlocksFieldBuilder().addBuilder(
          index, sawtooth.sdk.protobuf.ConsensusBlock.getDefaultInstance());
    }
    /**
     * <code>repeated .ConsensusBlock blocks = 2;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.ConsensusBlock.Builder> 
         getBlocksBuilderList() {
      return getBlocksFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> 
        getBlocksFieldBuilder() {
      if (blocksBuilder_ == null) {
        blocksBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder>(
                blocks_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        blocks_ = null;
      }
      return blocksBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ConsensusBlocksGetResponse)
  }

  // @@protoc_insertion_point(class_scope:ConsensusBlocksGetResponse)
  private static final sawtooth.sdk.protobuf.ConsensusBlocksGetResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.ConsensusBlocksGetResponse();
  }

  public static sawtooth.sdk.protobuf.ConsensusBlocksGetResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConsensusBlocksGetResponse>
      PARSER = new com.google.protobuf.AbstractParser<ConsensusBlocksGetResponse>() {
    @java.lang.Override
    public ConsensusBlocksGetResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ConsensusBlocksGetResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ConsensusBlocksGetResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConsensusBlocksGetResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.ConsensusBlocksGetResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

