// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: batch.proto

package sawtooth.sdk.protobuf;

/**
 * Protobuf type {@code Batch}
 */
public  final class Batch extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Batch)
    BatchOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Batch.newBuilder() to construct.
  private Batch(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Batch() {
    header_ = com.google.protobuf.ByteString.EMPTY;
    headerSignature_ = "";
    transactions_ = java.util.Collections.emptyList();
    trace_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Batch(
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

            header_ = input.readBytes();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            headerSignature_ = s;
            break;
          }
          case 26: {
            if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
              transactions_ = new java.util.ArrayList<sawtooth.sdk.protobuf.Transaction>();
              mutable_bitField0_ |= 0x00000004;
            }
            transactions_.add(
                input.readMessage(sawtooth.sdk.protobuf.Transaction.parser(), extensionRegistry));
            break;
          }
          case 32: {

            trace_ = input.readBool();
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
      if (((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
        transactions_ = java.util.Collections.unmodifiableList(transactions_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_Batch_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_Batch_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.Batch.class, sawtooth.sdk.protobuf.Batch.Builder.class);
  }

  private int bitField0_;
  public static final int HEADER_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString header_;
  /**
   * <pre>
   * The serialized version of the BatchHeader
   * </pre>
   *
   * <code>bytes header = 1;</code>
   */
  public com.google.protobuf.ByteString getHeader() {
    return header_;
  }

  public static final int HEADER_SIGNATURE_FIELD_NUMBER = 2;
  private volatile java.lang.Object headerSignature_;
  /**
   * <pre>
   * The signature derived from signing the header
   * </pre>
   *
   * <code>string header_signature = 2;</code>
   */
  public java.lang.String getHeaderSignature() {
    java.lang.Object ref = headerSignature_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      headerSignature_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * The signature derived from signing the header
   * </pre>
   *
   * <code>string header_signature = 2;</code>
   */
  public com.google.protobuf.ByteString
      getHeaderSignatureBytes() {
    java.lang.Object ref = headerSignature_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      headerSignature_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TRANSACTIONS_FIELD_NUMBER = 3;
  private java.util.List<sawtooth.sdk.protobuf.Transaction> transactions_;
  /**
   * <pre>
   * A list of the transactions that match the list of
   * transaction_ids listed in the batch header
   * </pre>
   *
   * <code>repeated .Transaction transactions = 3;</code>
   */
  public java.util.List<sawtooth.sdk.protobuf.Transaction> getTransactionsList() {
    return transactions_;
  }
  /**
   * <pre>
   * A list of the transactions that match the list of
   * transaction_ids listed in the batch header
   * </pre>
   *
   * <code>repeated .Transaction transactions = 3;</code>
   */
  public java.util.List<? extends sawtooth.sdk.protobuf.TransactionOrBuilder> 
      getTransactionsOrBuilderList() {
    return transactions_;
  }
  /**
   * <pre>
   * A list of the transactions that match the list of
   * transaction_ids listed in the batch header
   * </pre>
   *
   * <code>repeated .Transaction transactions = 3;</code>
   */
  public int getTransactionsCount() {
    return transactions_.size();
  }
  /**
   * <pre>
   * A list of the transactions that match the list of
   * transaction_ids listed in the batch header
   * </pre>
   *
   * <code>repeated .Transaction transactions = 3;</code>
   */
  public sawtooth.sdk.protobuf.Transaction getTransactions(int index) {
    return transactions_.get(index);
  }
  /**
   * <pre>
   * A list of the transactions that match the list of
   * transaction_ids listed in the batch header
   * </pre>
   *
   * <code>repeated .Transaction transactions = 3;</code>
   */
  public sawtooth.sdk.protobuf.TransactionOrBuilder getTransactionsOrBuilder(
      int index) {
    return transactions_.get(index);
  }

  public static final int TRACE_FIELD_NUMBER = 4;
  private boolean trace_;
  /**
   * <pre>
   * A debugging flag which indicates this batch should be traced through the
   * system, resulting in a higher level of debugging output.
   * </pre>
   *
   * <code>bool trace = 4;</code>
   */
  public boolean getTrace() {
    return trace_;
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
    if (!header_.isEmpty()) {
      output.writeBytes(1, header_);
    }
    if (!getHeaderSignatureBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, headerSignature_);
    }
    for (int i = 0; i < transactions_.size(); i++) {
      output.writeMessage(3, transactions_.get(i));
    }
    if (trace_ != false) {
      output.writeBool(4, trace_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!header_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, header_);
    }
    if (!getHeaderSignatureBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, headerSignature_);
    }
    for (int i = 0; i < transactions_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, transactions_.get(i));
    }
    if (trace_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(4, trace_);
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
    if (!(obj instanceof sawtooth.sdk.protobuf.Batch)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.Batch other = (sawtooth.sdk.protobuf.Batch) obj;

    boolean result = true;
    result = result && getHeader()
        .equals(other.getHeader());
    result = result && getHeaderSignature()
        .equals(other.getHeaderSignature());
    result = result && getTransactionsList()
        .equals(other.getTransactionsList());
    result = result && (getTrace()
        == other.getTrace());
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
    hash = (37 * hash) + HEADER_FIELD_NUMBER;
    hash = (53 * hash) + getHeader().hashCode();
    hash = (37 * hash) + HEADER_SIGNATURE_FIELD_NUMBER;
    hash = (53 * hash) + getHeaderSignature().hashCode();
    if (getTransactionsCount() > 0) {
      hash = (37 * hash) + TRANSACTIONS_FIELD_NUMBER;
      hash = (53 * hash) + getTransactionsList().hashCode();
    }
    hash = (37 * hash) + TRACE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getTrace());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.Batch parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.Batch parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.Batch parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.Batch parseFrom(
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
  public static Builder newBuilder(sawtooth.sdk.protobuf.Batch prototype) {
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
   * Protobuf type {@code Batch}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Batch)
      sawtooth.sdk.protobuf.BatchOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_Batch_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_Batch_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.Batch.class, sawtooth.sdk.protobuf.Batch.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.Batch.newBuilder()
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
        getTransactionsFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      header_ = com.google.protobuf.ByteString.EMPTY;

      headerSignature_ = "";

      if (transactionsBuilder_ == null) {
        transactions_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        transactionsBuilder_.clear();
      }
      trace_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_Batch_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.Batch getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.Batch.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.Batch build() {
      sawtooth.sdk.protobuf.Batch result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.Batch buildPartial() {
      sawtooth.sdk.protobuf.Batch result = new sawtooth.sdk.protobuf.Batch(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.header_ = header_;
      result.headerSignature_ = headerSignature_;
      if (transactionsBuilder_ == null) {
        if (((bitField0_ & 0x00000004) == 0x00000004)) {
          transactions_ = java.util.Collections.unmodifiableList(transactions_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.transactions_ = transactions_;
      } else {
        result.transactions_ = transactionsBuilder_.build();
      }
      result.trace_ = trace_;
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
      if (other instanceof sawtooth.sdk.protobuf.Batch) {
        return mergeFrom((sawtooth.sdk.protobuf.Batch)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.Batch other) {
      if (other == sawtooth.sdk.protobuf.Batch.getDefaultInstance()) return this;
      if (other.getHeader() != com.google.protobuf.ByteString.EMPTY) {
        setHeader(other.getHeader());
      }
      if (!other.getHeaderSignature().isEmpty()) {
        headerSignature_ = other.headerSignature_;
        onChanged();
      }
      if (transactionsBuilder_ == null) {
        if (!other.transactions_.isEmpty()) {
          if (transactions_.isEmpty()) {
            transactions_ = other.transactions_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureTransactionsIsMutable();
            transactions_.addAll(other.transactions_);
          }
          onChanged();
        }
      } else {
        if (!other.transactions_.isEmpty()) {
          if (transactionsBuilder_.isEmpty()) {
            transactionsBuilder_.dispose();
            transactionsBuilder_ = null;
            transactions_ = other.transactions_;
            bitField0_ = (bitField0_ & ~0x00000004);
            transactionsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getTransactionsFieldBuilder() : null;
          } else {
            transactionsBuilder_.addAllMessages(other.transactions_);
          }
        }
      }
      if (other.getTrace() != false) {
        setTrace(other.getTrace());
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
      sawtooth.sdk.protobuf.Batch parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.Batch) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.ByteString header_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * The serialized version of the BatchHeader
     * </pre>
     *
     * <code>bytes header = 1;</code>
     */
    public com.google.protobuf.ByteString getHeader() {
      return header_;
    }
    /**
     * <pre>
     * The serialized version of the BatchHeader
     * </pre>
     *
     * <code>bytes header = 1;</code>
     */
    public Builder setHeader(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      header_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The serialized version of the BatchHeader
     * </pre>
     *
     * <code>bytes header = 1;</code>
     */
    public Builder clearHeader() {
      
      header_ = getDefaultInstance().getHeader();
      onChanged();
      return this;
    }

    private java.lang.Object headerSignature_ = "";
    /**
     * <pre>
     * The signature derived from signing the header
     * </pre>
     *
     * <code>string header_signature = 2;</code>
     */
    public java.lang.String getHeaderSignature() {
      java.lang.Object ref = headerSignature_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        headerSignature_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * The signature derived from signing the header
     * </pre>
     *
     * <code>string header_signature = 2;</code>
     */
    public com.google.protobuf.ByteString
        getHeaderSignatureBytes() {
      java.lang.Object ref = headerSignature_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        headerSignature_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * The signature derived from signing the header
     * </pre>
     *
     * <code>string header_signature = 2;</code>
     */
    public Builder setHeaderSignature(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      headerSignature_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The signature derived from signing the header
     * </pre>
     *
     * <code>string header_signature = 2;</code>
     */
    public Builder clearHeaderSignature() {
      
      headerSignature_ = getDefaultInstance().getHeaderSignature();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The signature derived from signing the header
     * </pre>
     *
     * <code>string header_signature = 2;</code>
     */
    public Builder setHeaderSignatureBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      headerSignature_ = value;
      onChanged();
      return this;
    }

    private java.util.List<sawtooth.sdk.protobuf.Transaction> transactions_ =
      java.util.Collections.emptyList();
    private void ensureTransactionsIsMutable() {
      if (!((bitField0_ & 0x00000004) == 0x00000004)) {
        transactions_ = new java.util.ArrayList<sawtooth.sdk.protobuf.Transaction>(transactions_);
        bitField0_ |= 0x00000004;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.Transaction, sawtooth.sdk.protobuf.Transaction.Builder, sawtooth.sdk.protobuf.TransactionOrBuilder> transactionsBuilder_;

    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.Transaction> getTransactionsList() {
      if (transactionsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(transactions_);
      } else {
        return transactionsBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public int getTransactionsCount() {
      if (transactionsBuilder_ == null) {
        return transactions_.size();
      } else {
        return transactionsBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public sawtooth.sdk.protobuf.Transaction getTransactions(int index) {
      if (transactionsBuilder_ == null) {
        return transactions_.get(index);
      } else {
        return transactionsBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder setTransactions(
        int index, sawtooth.sdk.protobuf.Transaction value) {
      if (transactionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTransactionsIsMutable();
        transactions_.set(index, value);
        onChanged();
      } else {
        transactionsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder setTransactions(
        int index, sawtooth.sdk.protobuf.Transaction.Builder builderForValue) {
      if (transactionsBuilder_ == null) {
        ensureTransactionsIsMutable();
        transactions_.set(index, builderForValue.build());
        onChanged();
      } else {
        transactionsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder addTransactions(sawtooth.sdk.protobuf.Transaction value) {
      if (transactionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTransactionsIsMutable();
        transactions_.add(value);
        onChanged();
      } else {
        transactionsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder addTransactions(
        int index, sawtooth.sdk.protobuf.Transaction value) {
      if (transactionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTransactionsIsMutable();
        transactions_.add(index, value);
        onChanged();
      } else {
        transactionsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder addTransactions(
        sawtooth.sdk.protobuf.Transaction.Builder builderForValue) {
      if (transactionsBuilder_ == null) {
        ensureTransactionsIsMutable();
        transactions_.add(builderForValue.build());
        onChanged();
      } else {
        transactionsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder addTransactions(
        int index, sawtooth.sdk.protobuf.Transaction.Builder builderForValue) {
      if (transactionsBuilder_ == null) {
        ensureTransactionsIsMutable();
        transactions_.add(index, builderForValue.build());
        onChanged();
      } else {
        transactionsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder addAllTransactions(
        java.lang.Iterable<? extends sawtooth.sdk.protobuf.Transaction> values) {
      if (transactionsBuilder_ == null) {
        ensureTransactionsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, transactions_);
        onChanged();
      } else {
        transactionsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder clearTransactions() {
      if (transactionsBuilder_ == null) {
        transactions_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        transactionsBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public Builder removeTransactions(int index) {
      if (transactionsBuilder_ == null) {
        ensureTransactionsIsMutable();
        transactions_.remove(index);
        onChanged();
      } else {
        transactionsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public sawtooth.sdk.protobuf.Transaction.Builder getTransactionsBuilder(
        int index) {
      return getTransactionsFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public sawtooth.sdk.protobuf.TransactionOrBuilder getTransactionsOrBuilder(
        int index) {
      if (transactionsBuilder_ == null) {
        return transactions_.get(index);  } else {
        return transactionsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public java.util.List<? extends sawtooth.sdk.protobuf.TransactionOrBuilder> 
         getTransactionsOrBuilderList() {
      if (transactionsBuilder_ != null) {
        return transactionsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(transactions_);
      }
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public sawtooth.sdk.protobuf.Transaction.Builder addTransactionsBuilder() {
      return getTransactionsFieldBuilder().addBuilder(
          sawtooth.sdk.protobuf.Transaction.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public sawtooth.sdk.protobuf.Transaction.Builder addTransactionsBuilder(
        int index) {
      return getTransactionsFieldBuilder().addBuilder(
          index, sawtooth.sdk.protobuf.Transaction.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of the transactions that match the list of
     * transaction_ids listed in the batch header
     * </pre>
     *
     * <code>repeated .Transaction transactions = 3;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.Transaction.Builder> 
         getTransactionsBuilderList() {
      return getTransactionsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.Transaction, sawtooth.sdk.protobuf.Transaction.Builder, sawtooth.sdk.protobuf.TransactionOrBuilder> 
        getTransactionsFieldBuilder() {
      if (transactionsBuilder_ == null) {
        transactionsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            sawtooth.sdk.protobuf.Transaction, sawtooth.sdk.protobuf.Transaction.Builder, sawtooth.sdk.protobuf.TransactionOrBuilder>(
                transactions_,
                ((bitField0_ & 0x00000004) == 0x00000004),
                getParentForChildren(),
                isClean());
        transactions_ = null;
      }
      return transactionsBuilder_;
    }

    private boolean trace_ ;
    /**
     * <pre>
     * A debugging flag which indicates this batch should be traced through the
     * system, resulting in a higher level of debugging output.
     * </pre>
     *
     * <code>bool trace = 4;</code>
     */
    public boolean getTrace() {
      return trace_;
    }
    /**
     * <pre>
     * A debugging flag which indicates this batch should be traced through the
     * system, resulting in a higher level of debugging output.
     * </pre>
     *
     * <code>bool trace = 4;</code>
     */
    public Builder setTrace(boolean value) {
      
      trace_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A debugging flag which indicates this batch should be traced through the
     * system, resulting in a higher level of debugging output.
     * </pre>
     *
     * <code>bool trace = 4;</code>
     */
    public Builder clearTrace() {
      
      trace_ = false;
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


    // @@protoc_insertion_point(builder_scope:Batch)
  }

  // @@protoc_insertion_point(class_scope:Batch)
  private static final sawtooth.sdk.protobuf.Batch DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.Batch();
  }

  public static sawtooth.sdk.protobuf.Batch getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Batch>
      PARSER = new com.google.protobuf.AbstractParser<Batch>() {
    @java.lang.Override
    public Batch parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Batch(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Batch> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Batch> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.Batch getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
