// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: network.proto

package sawtooth.sdk.protobuf;

public interface GossipBatchByTransactionIdRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:GossipBatchByTransactionIdRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The id's of the transaction that are in the batches requested
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  java.util.List<java.lang.String>
      getIdsList();
  /**
   * <pre>
   * The id's of the transaction that are in the batches requested
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  int getIdsCount();
  /**
   * <pre>
   * The id's of the transaction that are in the batches requested
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  java.lang.String getIds(int index);
  /**
   * <pre>
   * The id's of the transaction that are in the batches requested
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  com.google.protobuf.ByteString
      getIdsBytes(int index);

  /**
   * <pre>
   * A random string that provides uniqueness for requests with
   * otherwise identical fields.
   * </pre>
   *
   * <code>string nonce = 2;</code>
   */
  java.lang.String getNonce();
  /**
   * <pre>
   * A random string that provides uniqueness for requests with
   * otherwise identical fields.
   * </pre>
   *
   * <code>string nonce = 2;</code>
   */
  com.google.protobuf.ByteString
      getNonceBytes();

  /**
   * <code>uint32 time_to_live = 3;</code>
   */
  int getTimeToLive();
}