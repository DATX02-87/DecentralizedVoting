// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consensus.proto

package sawtooth.sdk.protobuf;

public interface ConsensusSettingsGetResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ConsensusSettingsGetResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.ConsensusSettingsGetResponse.Status status = 1;</code>
   */
  int getStatusValue();
  /**
   * <code>.ConsensusSettingsGetResponse.Status status = 1;</code>
   */
  sawtooth.sdk.protobuf.ConsensusSettingsGetResponse.Status getStatus();

  /**
   * <code>repeated .ConsensusSettingsEntry entries = 2;</code>
   */
  java.util.List<sawtooth.sdk.protobuf.ConsensusSettingsEntry> 
      getEntriesList();
  /**
   * <code>repeated .ConsensusSettingsEntry entries = 2;</code>
   */
  sawtooth.sdk.protobuf.ConsensusSettingsEntry getEntries(int index);
  /**
   * <code>repeated .ConsensusSettingsEntry entries = 2;</code>
   */
  int getEntriesCount();
  /**
   * <code>repeated .ConsensusSettingsEntry entries = 2;</code>
   */
  java.util.List<? extends sawtooth.sdk.protobuf.ConsensusSettingsEntryOrBuilder> 
      getEntriesOrBuilderList();
  /**
   * <code>repeated .ConsensusSettingsEntry entries = 2;</code>
   */
  sawtooth.sdk.protobuf.ConsensusSettingsEntryOrBuilder getEntriesOrBuilder(
      int index);
}
