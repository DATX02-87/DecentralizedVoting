// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: client_batch_submit.proto

package sawtooth.sdk.protobuf;

/**
 * <pre>
 * Submits a list of Batches to be added to the blockchain.
 * </pre>
 *
 * Protobuf type {@code ClientBatchSubmitRequest}
 */
public  final class ClientBatchSubmitRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ClientBatchSubmitRequest)
    ClientBatchSubmitRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ClientBatchSubmitRequest.newBuilder() to construct.
  private ClientBatchSubmitRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ClientBatchSubmitRequest() {
    batches_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ClientBatchSubmitRequest(
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
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              batches_ = new java.util.ArrayList<sawtooth.sdk.protobuf.Batch>();
              mutable_bitField0_ |= 0x00000001;
            }
            batches_.add(
                input.readMessage(sawtooth.sdk.protobuf.Batch.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        batches_ = java.util.Collections.unmodifiableList(batches_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.ClientBatchSubmit.internal_static_ClientBatchSubmitRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.ClientBatchSubmit.internal_static_ClientBatchSubmitRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.ClientBatchSubmitRequest.class, sawtooth.sdk.protobuf.ClientBatchSubmitRequest.Builder.class);
  }

  public static final int BATCHES_FIELD_NUMBER = 1;
  private java.util.List<sawtooth.sdk.protobuf.Batch> batches_;
  /**
   * <code>repeated .Batch batches = 1;</code>
   */
  public java.util.List<sawtooth.sdk.protobuf.Batch> getBatchesList() {
    return batches_;
  }
  /**
   * <code>repeated .Batch batches = 1;</code>
   */
  public java.util.List<? extends sawtooth.sdk.protobuf.BatchOrBuilder> 
      getBatchesOrBuilderList() {
    return batches_;
  }
  /**
   * <code>repeated .Batch batches = 1;</code>
   */
  public int getBatchesCount() {
    return batches_.size();
  }
  /**
   * <code>repeated .Batch batches = 1;</code>
   */
  public sawtooth.sdk.protobuf.Batch getBatches(int index) {
    return batches_.get(index);
  }
  /**
   * <code>repeated .Batch batches = 1;</code>
   */
  public sawtooth.sdk.protobuf.BatchOrBuilder getBatchesOrBuilder(
      int index) {
    return batches_.get(index);
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
    for (int i = 0; i < batches_.size(); i++) {
      output.writeMessage(1, batches_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < batches_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, batches_.get(i));
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
    if (!(obj instanceof sawtooth.sdk.protobuf.ClientBatchSubmitRequest)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.ClientBatchSubmitRequest other = (sawtooth.sdk.protobuf.ClientBatchSubmitRequest) obj;

    boolean result = true;
    result = result && getBatchesList()
        .equals(other.getBatchesList());
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
    if (getBatchesCount() > 0) {
      hash = (37 * hash) + BATCHES_FIELD_NUMBER;
      hash = (53 * hash) + getBatchesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest parseFrom(
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
  public static Builder newBuilder(sawtooth.sdk.protobuf.ClientBatchSubmitRequest prototype) {
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
   * Submits a list of Batches to be added to the blockchain.
   * </pre>
   *
   * Protobuf type {@code ClientBatchSubmitRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ClientBatchSubmitRequest)
      sawtooth.sdk.protobuf.ClientBatchSubmitRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.ClientBatchSubmit.internal_static_ClientBatchSubmitRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.ClientBatchSubmit.internal_static_ClientBatchSubmitRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.ClientBatchSubmitRequest.class, sawtooth.sdk.protobuf.ClientBatchSubmitRequest.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.ClientBatchSubmitRequest.newBuilder()
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
        getBatchesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (batchesBuilder_ == null) {
        batches_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        batchesBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.ClientBatchSubmit.internal_static_ClientBatchSubmitRequest_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ClientBatchSubmitRequest getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.ClientBatchSubmitRequest.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ClientBatchSubmitRequest build() {
      sawtooth.sdk.protobuf.ClientBatchSubmitRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ClientBatchSubmitRequest buildPartial() {
      sawtooth.sdk.protobuf.ClientBatchSubmitRequest result = new sawtooth.sdk.protobuf.ClientBatchSubmitRequest(this);
      int from_bitField0_ = bitField0_;
      if (batchesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          batches_ = java.util.Collections.unmodifiableList(batches_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.batches_ = batches_;
      } else {
        result.batches_ = batchesBuilder_.build();
      }
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
      if (other instanceof sawtooth.sdk.protobuf.ClientBatchSubmitRequest) {
        return mergeFrom((sawtooth.sdk.protobuf.ClientBatchSubmitRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.ClientBatchSubmitRequest other) {
      if (other == sawtooth.sdk.protobuf.ClientBatchSubmitRequest.getDefaultInstance()) return this;
      if (batchesBuilder_ == null) {
        if (!other.batches_.isEmpty()) {
          if (batches_.isEmpty()) {
            batches_ = other.batches_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBatchesIsMutable();
            batches_.addAll(other.batches_);
          }
          onChanged();
        }
      } else {
        if (!other.batches_.isEmpty()) {
          if (batchesBuilder_.isEmpty()) {
            batchesBuilder_.dispose();
            batchesBuilder_ = null;
            batches_ = other.batches_;
            bitField0_ = (bitField0_ & ~0x00000001);
            batchesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBatchesFieldBuilder() : null;
          } else {
            batchesBuilder_.addAllMessages(other.batches_);
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
      sawtooth.sdk.protobuf.ClientBatchSubmitRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.ClientBatchSubmitRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<sawtooth.sdk.protobuf.Batch> batches_ =
      java.util.Collections.emptyList();
    private void ensureBatchesIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        batches_ = new java.util.ArrayList<sawtooth.sdk.protobuf.Batch>(batches_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.Batch, sawtooth.sdk.protobuf.Batch.Builder, sawtooth.sdk.protobuf.BatchOrBuilder> batchesBuilder_;

    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.Batch> getBatchesList() {
      if (batchesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(batches_);
      } else {
        return batchesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public int getBatchesCount() {
      if (batchesBuilder_ == null) {
        return batches_.size();
      } else {
        return batchesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public sawtooth.sdk.protobuf.Batch getBatches(int index) {
      if (batchesBuilder_ == null) {
        return batches_.get(index);
      } else {
        return batchesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder setBatches(
        int index, sawtooth.sdk.protobuf.Batch value) {
      if (batchesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBatchesIsMutable();
        batches_.set(index, value);
        onChanged();
      } else {
        batchesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder setBatches(
        int index, sawtooth.sdk.protobuf.Batch.Builder builderForValue) {
      if (batchesBuilder_ == null) {
        ensureBatchesIsMutable();
        batches_.set(index, builderForValue.build());
        onChanged();
      } else {
        batchesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder addBatches(sawtooth.sdk.protobuf.Batch value) {
      if (batchesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBatchesIsMutable();
        batches_.add(value);
        onChanged();
      } else {
        batchesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder addBatches(
        int index, sawtooth.sdk.protobuf.Batch value) {
      if (batchesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBatchesIsMutable();
        batches_.add(index, value);
        onChanged();
      } else {
        batchesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder addBatches(
        sawtooth.sdk.protobuf.Batch.Builder builderForValue) {
      if (batchesBuilder_ == null) {
        ensureBatchesIsMutable();
        batches_.add(builderForValue.build());
        onChanged();
      } else {
        batchesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder addBatches(
        int index, sawtooth.sdk.protobuf.Batch.Builder builderForValue) {
      if (batchesBuilder_ == null) {
        ensureBatchesIsMutable();
        batches_.add(index, builderForValue.build());
        onChanged();
      } else {
        batchesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder addAllBatches(
        java.lang.Iterable<? extends sawtooth.sdk.protobuf.Batch> values) {
      if (batchesBuilder_ == null) {
        ensureBatchesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, batches_);
        onChanged();
      } else {
        batchesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder clearBatches() {
      if (batchesBuilder_ == null) {
        batches_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        batchesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public Builder removeBatches(int index) {
      if (batchesBuilder_ == null) {
        ensureBatchesIsMutable();
        batches_.remove(index);
        onChanged();
      } else {
        batchesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public sawtooth.sdk.protobuf.Batch.Builder getBatchesBuilder(
        int index) {
      return getBatchesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public sawtooth.sdk.protobuf.BatchOrBuilder getBatchesOrBuilder(
        int index) {
      if (batchesBuilder_ == null) {
        return batches_.get(index);  } else {
        return batchesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public java.util.List<? extends sawtooth.sdk.protobuf.BatchOrBuilder> 
         getBatchesOrBuilderList() {
      if (batchesBuilder_ != null) {
        return batchesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(batches_);
      }
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public sawtooth.sdk.protobuf.Batch.Builder addBatchesBuilder() {
      return getBatchesFieldBuilder().addBuilder(
          sawtooth.sdk.protobuf.Batch.getDefaultInstance());
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public sawtooth.sdk.protobuf.Batch.Builder addBatchesBuilder(
        int index) {
      return getBatchesFieldBuilder().addBuilder(
          index, sawtooth.sdk.protobuf.Batch.getDefaultInstance());
    }
    /**
     * <code>repeated .Batch batches = 1;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.Batch.Builder> 
         getBatchesBuilderList() {
      return getBatchesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.Batch, sawtooth.sdk.protobuf.Batch.Builder, sawtooth.sdk.protobuf.BatchOrBuilder> 
        getBatchesFieldBuilder() {
      if (batchesBuilder_ == null) {
        batchesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            sawtooth.sdk.protobuf.Batch, sawtooth.sdk.protobuf.Batch.Builder, sawtooth.sdk.protobuf.BatchOrBuilder>(
                batches_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        batches_ = null;
      }
      return batchesBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ClientBatchSubmitRequest)
  }

  // @@protoc_insertion_point(class_scope:ClientBatchSubmitRequest)
  private static final sawtooth.sdk.protobuf.ClientBatchSubmitRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.ClientBatchSubmitRequest();
  }

  public static sawtooth.sdk.protobuf.ClientBatchSubmitRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ClientBatchSubmitRequest>
      PARSER = new com.google.protobuf.AbstractParser<ClientBatchSubmitRequest>() {
    @java.lang.Override
    public ClientBatchSubmitRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ClientBatchSubmitRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ClientBatchSubmitRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ClientBatchSubmitRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.ClientBatchSubmitRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
