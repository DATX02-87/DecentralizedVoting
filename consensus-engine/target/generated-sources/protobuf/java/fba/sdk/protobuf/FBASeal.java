// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: fba_message.proto

package fba.sdk.protobuf;

/**
 * Protobuf type {@code FBASeal}
 */
public  final class FBASeal extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:FBASeal)
    FBASealOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FBASeal.newBuilder() to construct.
  private FBASeal(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FBASeal() {
    blockId_ = com.google.protobuf.ByteString.EMPTY;
    commitVotes_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private FBASeal(
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
            fba.sdk.protobuf.FBAMessageInfo.Builder subBuilder = null;
            if (info_ != null) {
              subBuilder = info_.toBuilder();
            }
            info_ = input.readMessage(fba.sdk.protobuf.FBAMessageInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(info_);
              info_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {

            blockId_ = input.readBytes();
            break;
          }
          case 26: {
            if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
              commitVotes_ = new java.util.ArrayList<fba.sdk.protobuf.FBASignedVote>();
              mutable_bitField0_ |= 0x00000004;
            }
            commitVotes_.add(
                input.readMessage(fba.sdk.protobuf.FBASignedVote.parser(), extensionRegistry));
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
        commitVotes_ = java.util.Collections.unmodifiableList(commitVotes_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return fba.sdk.protobuf.FbaMessage.internal_static_FBASeal_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return fba.sdk.protobuf.FbaMessage.internal_static_FBASeal_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            fba.sdk.protobuf.FBASeal.class, fba.sdk.protobuf.FBASeal.Builder.class);
  }

  private int bitField0_;
  public static final int INFO_FIELD_NUMBER = 1;
  private fba.sdk.protobuf.FBAMessageInfo info_;
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.FBAMessageInfo info = 1;</code>
   */
  public boolean hasInfo() {
    return info_ != null;
  }
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.FBAMessageInfo info = 1;</code>
   */
  public fba.sdk.protobuf.FBAMessageInfo getInfo() {
    return info_ == null ? fba.sdk.protobuf.FBAMessageInfo.getDefaultInstance() : info_;
  }
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.FBAMessageInfo info = 1;</code>
   */
  public fba.sdk.protobuf.FBAMessageInfoOrBuilder getInfoOrBuilder() {
    return getInfo();
  }

  public static final int BLOCK_ID_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString blockId_;
  /**
   * <pre>
   * ID of the block this seal verifies
   * </pre>
   *
   * <code>bytes block_id = 2;</code>
   */
  public com.google.protobuf.ByteString getBlockId() {
    return blockId_;
  }

  public static final int COMMIT_VOTES_FIELD_NUMBER = 3;
  private java.util.List<fba.sdk.protobuf.FBASignedVote> commitVotes_;
  /**
   * <pre>
   * A list of Commit votes to prove the block commit (must contain at least 2f
   * votes)
   * </pre>
   *
   * <code>repeated .FBASignedVote commit_votes = 3;</code>
   */
  public java.util.List<fba.sdk.protobuf.FBASignedVote> getCommitVotesList() {
    return commitVotes_;
  }
  /**
   * <pre>
   * A list of Commit votes to prove the block commit (must contain at least 2f
   * votes)
   * </pre>
   *
   * <code>repeated .FBASignedVote commit_votes = 3;</code>
   */
  public java.util.List<? extends fba.sdk.protobuf.FBASignedVoteOrBuilder> 
      getCommitVotesOrBuilderList() {
    return commitVotes_;
  }
  /**
   * <pre>
   * A list of Commit votes to prove the block commit (must contain at least 2f
   * votes)
   * </pre>
   *
   * <code>repeated .FBASignedVote commit_votes = 3;</code>
   */
  public int getCommitVotesCount() {
    return commitVotes_.size();
  }
  /**
   * <pre>
   * A list of Commit votes to prove the block commit (must contain at least 2f
   * votes)
   * </pre>
   *
   * <code>repeated .FBASignedVote commit_votes = 3;</code>
   */
  public fba.sdk.protobuf.FBASignedVote getCommitVotes(int index) {
    return commitVotes_.get(index);
  }
  /**
   * <pre>
   * A list of Commit votes to prove the block commit (must contain at least 2f
   * votes)
   * </pre>
   *
   * <code>repeated .FBASignedVote commit_votes = 3;</code>
   */
  public fba.sdk.protobuf.FBASignedVoteOrBuilder getCommitVotesOrBuilder(
      int index) {
    return commitVotes_.get(index);
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
    if (info_ != null) {
      output.writeMessage(1, getInfo());
    }
    if (!blockId_.isEmpty()) {
      output.writeBytes(2, blockId_);
    }
    for (int i = 0; i < commitVotes_.size(); i++) {
      output.writeMessage(3, commitVotes_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (info_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getInfo());
    }
    if (!blockId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, blockId_);
    }
    for (int i = 0; i < commitVotes_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, commitVotes_.get(i));
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
    if (!(obj instanceof fba.sdk.protobuf.FBASeal)) {
      return super.equals(obj);
    }
    fba.sdk.protobuf.FBASeal other = (fba.sdk.protobuf.FBASeal) obj;

    boolean result = true;
    result = result && (hasInfo() == other.hasInfo());
    if (hasInfo()) {
      result = result && getInfo()
          .equals(other.getInfo());
    }
    result = result && getBlockId()
        .equals(other.getBlockId());
    result = result && getCommitVotesList()
        .equals(other.getCommitVotesList());
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
    if (hasInfo()) {
      hash = (37 * hash) + INFO_FIELD_NUMBER;
      hash = (53 * hash) + getInfo().hashCode();
    }
    hash = (37 * hash) + BLOCK_ID_FIELD_NUMBER;
    hash = (53 * hash) + getBlockId().hashCode();
    if (getCommitVotesCount() > 0) {
      hash = (37 * hash) + COMMIT_VOTES_FIELD_NUMBER;
      hash = (53 * hash) + getCommitVotesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static fba.sdk.protobuf.FBASeal parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBASeal parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBASeal parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fba.sdk.protobuf.FBASeal parseFrom(
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
  public static Builder newBuilder(fba.sdk.protobuf.FBASeal prototype) {
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
   * Protobuf type {@code FBASeal}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:FBASeal)
      fba.sdk.protobuf.FBASealOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBASeal_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBASeal_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              fba.sdk.protobuf.FBASeal.class, fba.sdk.protobuf.FBASeal.Builder.class);
    }

    // Construct using fba.sdk.protobuf.FBASeal.newBuilder()
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
        getCommitVotesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (infoBuilder_ == null) {
        info_ = null;
      } else {
        info_ = null;
        infoBuilder_ = null;
      }
      blockId_ = com.google.protobuf.ByteString.EMPTY;

      if (commitVotesBuilder_ == null) {
        commitVotes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        commitVotesBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return fba.sdk.protobuf.FbaMessage.internal_static_FBASeal_descriptor;
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBASeal getDefaultInstanceForType() {
      return fba.sdk.protobuf.FBASeal.getDefaultInstance();
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBASeal build() {
      fba.sdk.protobuf.FBASeal result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public fba.sdk.protobuf.FBASeal buildPartial() {
      fba.sdk.protobuf.FBASeal result = new fba.sdk.protobuf.FBASeal(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (infoBuilder_ == null) {
        result.info_ = info_;
      } else {
        result.info_ = infoBuilder_.build();
      }
      result.blockId_ = blockId_;
      if (commitVotesBuilder_ == null) {
        if (((bitField0_ & 0x00000004) == 0x00000004)) {
          commitVotes_ = java.util.Collections.unmodifiableList(commitVotes_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.commitVotes_ = commitVotes_;
      } else {
        result.commitVotes_ = commitVotesBuilder_.build();
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
      if (other instanceof fba.sdk.protobuf.FBASeal) {
        return mergeFrom((fba.sdk.protobuf.FBASeal)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(fba.sdk.protobuf.FBASeal other) {
      if (other == fba.sdk.protobuf.FBASeal.getDefaultInstance()) return this;
      if (other.hasInfo()) {
        mergeInfo(other.getInfo());
      }
      if (other.getBlockId() != com.google.protobuf.ByteString.EMPTY) {
        setBlockId(other.getBlockId());
      }
      if (commitVotesBuilder_ == null) {
        if (!other.commitVotes_.isEmpty()) {
          if (commitVotes_.isEmpty()) {
            commitVotes_ = other.commitVotes_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureCommitVotesIsMutable();
            commitVotes_.addAll(other.commitVotes_);
          }
          onChanged();
        }
      } else {
        if (!other.commitVotes_.isEmpty()) {
          if (commitVotesBuilder_.isEmpty()) {
            commitVotesBuilder_.dispose();
            commitVotesBuilder_ = null;
            commitVotes_ = other.commitVotes_;
            bitField0_ = (bitField0_ & ~0x00000004);
            commitVotesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getCommitVotesFieldBuilder() : null;
          } else {
            commitVotesBuilder_.addAllMessages(other.commitVotes_);
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
      fba.sdk.protobuf.FBASeal parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (fba.sdk.protobuf.FBASeal) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private fba.sdk.protobuf.FBAMessageInfo info_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        fba.sdk.protobuf.FBAMessageInfo, fba.sdk.protobuf.FBAMessageInfo.Builder, fba.sdk.protobuf.FBAMessageInfoOrBuilder> infoBuilder_;
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public boolean hasInfo() {
      return infoBuilder_ != null || info_ != null;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public fba.sdk.protobuf.FBAMessageInfo getInfo() {
      if (infoBuilder_ == null) {
        return info_ == null ? fba.sdk.protobuf.FBAMessageInfo.getDefaultInstance() : info_;
      } else {
        return infoBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public Builder setInfo(fba.sdk.protobuf.FBAMessageInfo value) {
      if (infoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        info_ = value;
        onChanged();
      } else {
        infoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public Builder setInfo(
        fba.sdk.protobuf.FBAMessageInfo.Builder builderForValue) {
      if (infoBuilder_ == null) {
        info_ = builderForValue.build();
        onChanged();
      } else {
        infoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public Builder mergeInfo(fba.sdk.protobuf.FBAMessageInfo value) {
      if (infoBuilder_ == null) {
        if (info_ != null) {
          info_ =
            fba.sdk.protobuf.FBAMessageInfo.newBuilder(info_).mergeFrom(value).buildPartial();
        } else {
          info_ = value;
        }
        onChanged();
      } else {
        infoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public Builder clearInfo() {
      if (infoBuilder_ == null) {
        info_ = null;
        onChanged();
      } else {
        info_ = null;
        infoBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public fba.sdk.protobuf.FBAMessageInfo.Builder getInfoBuilder() {
      
      onChanged();
      return getInfoFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    public fba.sdk.protobuf.FBAMessageInfoOrBuilder getInfoOrBuilder() {
      if (infoBuilder_ != null) {
        return infoBuilder_.getMessageOrBuilder();
      } else {
        return info_ == null ?
            fba.sdk.protobuf.FBAMessageInfo.getDefaultInstance() : info_;
      }
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.FBAMessageInfo info = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        fba.sdk.protobuf.FBAMessageInfo, fba.sdk.protobuf.FBAMessageInfo.Builder, fba.sdk.protobuf.FBAMessageInfoOrBuilder> 
        getInfoFieldBuilder() {
      if (infoBuilder_ == null) {
        infoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            fba.sdk.protobuf.FBAMessageInfo, fba.sdk.protobuf.FBAMessageInfo.Builder, fba.sdk.protobuf.FBAMessageInfoOrBuilder>(
                getInfo(),
                getParentForChildren(),
                isClean());
        info_ = null;
      }
      return infoBuilder_;
    }

    private com.google.protobuf.ByteString blockId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * ID of the block this seal verifies
     * </pre>
     *
     * <code>bytes block_id = 2;</code>
     */
    public com.google.protobuf.ByteString getBlockId() {
      return blockId_;
    }
    /**
     * <pre>
     * ID of the block this seal verifies
     * </pre>
     *
     * <code>bytes block_id = 2;</code>
     */
    public Builder setBlockId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      blockId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * ID of the block this seal verifies
     * </pre>
     *
     * <code>bytes block_id = 2;</code>
     */
    public Builder clearBlockId() {
      
      blockId_ = getDefaultInstance().getBlockId();
      onChanged();
      return this;
    }

    private java.util.List<fba.sdk.protobuf.FBASignedVote> commitVotes_ =
      java.util.Collections.emptyList();
    private void ensureCommitVotesIsMutable() {
      if (!((bitField0_ & 0x00000004) == 0x00000004)) {
        commitVotes_ = new java.util.ArrayList<fba.sdk.protobuf.FBASignedVote>(commitVotes_);
        bitField0_ |= 0x00000004;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        fba.sdk.protobuf.FBASignedVote, fba.sdk.protobuf.FBASignedVote.Builder, fba.sdk.protobuf.FBASignedVoteOrBuilder> commitVotesBuilder_;

    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public java.util.List<fba.sdk.protobuf.FBASignedVote> getCommitVotesList() {
      if (commitVotesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(commitVotes_);
      } else {
        return commitVotesBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public int getCommitVotesCount() {
      if (commitVotesBuilder_ == null) {
        return commitVotes_.size();
      } else {
        return commitVotesBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public fba.sdk.protobuf.FBASignedVote getCommitVotes(int index) {
      if (commitVotesBuilder_ == null) {
        return commitVotes_.get(index);
      } else {
        return commitVotesBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder setCommitVotes(
        int index, fba.sdk.protobuf.FBASignedVote value) {
      if (commitVotesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommitVotesIsMutable();
        commitVotes_.set(index, value);
        onChanged();
      } else {
        commitVotesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder setCommitVotes(
        int index, fba.sdk.protobuf.FBASignedVote.Builder builderForValue) {
      if (commitVotesBuilder_ == null) {
        ensureCommitVotesIsMutable();
        commitVotes_.set(index, builderForValue.build());
        onChanged();
      } else {
        commitVotesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder addCommitVotes(fba.sdk.protobuf.FBASignedVote value) {
      if (commitVotesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommitVotesIsMutable();
        commitVotes_.add(value);
        onChanged();
      } else {
        commitVotesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder addCommitVotes(
        int index, fba.sdk.protobuf.FBASignedVote value) {
      if (commitVotesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommitVotesIsMutable();
        commitVotes_.add(index, value);
        onChanged();
      } else {
        commitVotesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder addCommitVotes(
        fba.sdk.protobuf.FBASignedVote.Builder builderForValue) {
      if (commitVotesBuilder_ == null) {
        ensureCommitVotesIsMutable();
        commitVotes_.add(builderForValue.build());
        onChanged();
      } else {
        commitVotesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder addCommitVotes(
        int index, fba.sdk.protobuf.FBASignedVote.Builder builderForValue) {
      if (commitVotesBuilder_ == null) {
        ensureCommitVotesIsMutable();
        commitVotes_.add(index, builderForValue.build());
        onChanged();
      } else {
        commitVotesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder addAllCommitVotes(
        java.lang.Iterable<? extends fba.sdk.protobuf.FBASignedVote> values) {
      if (commitVotesBuilder_ == null) {
        ensureCommitVotesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, commitVotes_);
        onChanged();
      } else {
        commitVotesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder clearCommitVotes() {
      if (commitVotesBuilder_ == null) {
        commitVotes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        commitVotesBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public Builder removeCommitVotes(int index) {
      if (commitVotesBuilder_ == null) {
        ensureCommitVotesIsMutable();
        commitVotes_.remove(index);
        onChanged();
      } else {
        commitVotesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public fba.sdk.protobuf.FBASignedVote.Builder getCommitVotesBuilder(
        int index) {
      return getCommitVotesFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public fba.sdk.protobuf.FBASignedVoteOrBuilder getCommitVotesOrBuilder(
        int index) {
      if (commitVotesBuilder_ == null) {
        return commitVotes_.get(index);  } else {
        return commitVotesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public java.util.List<? extends fba.sdk.protobuf.FBASignedVoteOrBuilder> 
         getCommitVotesOrBuilderList() {
      if (commitVotesBuilder_ != null) {
        return commitVotesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(commitVotes_);
      }
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public fba.sdk.protobuf.FBASignedVote.Builder addCommitVotesBuilder() {
      return getCommitVotesFieldBuilder().addBuilder(
          fba.sdk.protobuf.FBASignedVote.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public fba.sdk.protobuf.FBASignedVote.Builder addCommitVotesBuilder(
        int index) {
      return getCommitVotesFieldBuilder().addBuilder(
          index, fba.sdk.protobuf.FBASignedVote.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of Commit votes to prove the block commit (must contain at least 2f
     * votes)
     * </pre>
     *
     * <code>repeated .FBASignedVote commit_votes = 3;</code>
     */
    public java.util.List<fba.sdk.protobuf.FBASignedVote.Builder> 
         getCommitVotesBuilderList() {
      return getCommitVotesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        fba.sdk.protobuf.FBASignedVote, fba.sdk.protobuf.FBASignedVote.Builder, fba.sdk.protobuf.FBASignedVoteOrBuilder> 
        getCommitVotesFieldBuilder() {
      if (commitVotesBuilder_ == null) {
        commitVotesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            fba.sdk.protobuf.FBASignedVote, fba.sdk.protobuf.FBASignedVote.Builder, fba.sdk.protobuf.FBASignedVoteOrBuilder>(
                commitVotes_,
                ((bitField0_ & 0x00000004) == 0x00000004),
                getParentForChildren(),
                isClean());
        commitVotes_ = null;
      }
      return commitVotesBuilder_;
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


    // @@protoc_insertion_point(builder_scope:FBASeal)
  }

  // @@protoc_insertion_point(class_scope:FBASeal)
  private static final fba.sdk.protobuf.FBASeal DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new fba.sdk.protobuf.FBASeal();
  }

  public static fba.sdk.protobuf.FBASeal getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FBASeal>
      PARSER = new com.google.protobuf.AbstractParser<FBASeal>() {
    @java.lang.Override
    public FBASeal parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new FBASeal(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FBASeal> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<FBASeal> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public fba.sdk.protobuf.FBASeal getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

