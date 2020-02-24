// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: client_state.proto

package sawtooth.sdk.protobuf;

public final class ClientState {
  private ClientState() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientStateListRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientStateListRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientStateListResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientStateListResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientStateListResponse_Entry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientStateListResponse_Entry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientStateGetRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientStateGetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientStateGetResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientStateGetResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022client_state.proto\032\031client_list_contro" +
      "l.proto\"\212\001\n\026ClientStateListRequest\022\022\n\nst" +
      "ate_root\030\001 \001(\t\022\017\n\007address\030\003 \001(\t\022%\n\006pagin" +
      "g\030\004 \001(\0132\025.ClientPagingControls\022$\n\007sortin" +
      "g\030\005 \003(\0132\023.ClientSortControls\"\221\003\n\027ClientS" +
      "tateListResponse\022/\n\006status\030\001 \001(\0162\037.Clien" +
      "tStateListResponse.Status\022/\n\007entries\030\002 \003" +
      "(\0132\036.ClientStateListResponse.Entry\022\022\n\nst" +
      "ate_root\030\003 \001(\t\022%\n\006paging\030\004 \001(\0132\025.ClientP" +
      "agingResponse\032&\n\005Entry\022\017\n\007address\030\001 \001(\t\022" +
      "\014\n\004data\030\002 \001(\014\"\260\001\n\006Status\022\020\n\014STATUS_UNSET" +
      "\020\000\022\006\n\002OK\020\001\022\022\n\016INTERNAL_ERROR\020\002\022\r\n\tNOT_RE" +
      "ADY\020\003\022\013\n\007NO_ROOT\020\004\022\017\n\013NO_RESOURCE\020\005\022\022\n\016I" +
      "NVALID_PAGING\020\006\022\020\n\014INVALID_SORT\020\007\022\023\n\017INV" +
      "ALID_ADDRESS\020\010\022\020\n\014INVALID_ROOT\020\t\"<\n\025Clie" +
      "ntStateGetRequest\022\022\n\nstate_root\030\001 \001(\t\022\017\n" +
      "\007address\030\003 \001(\t\"\370\001\n\026ClientStateGetRespons" +
      "e\022.\n\006status\030\001 \001(\0162\036.ClientStateGetRespon" +
      "se.Status\022\r\n\005value\030\002 \001(\014\022\022\n\nstate_root\030\003" +
      " \001(\t\"\212\001\n\006Status\022\020\n\014STATUS_UNSET\020\000\022\006\n\002OK\020" +
      "\001\022\022\n\016INTERNAL_ERROR\020\002\022\r\n\tNOT_READY\020\003\022\013\n\007" +
      "NO_ROOT\020\004\022\017\n\013NO_RESOURCE\020\005\022\023\n\017INVALID_AD" +
      "DRESS\020\006\022\020\n\014INVALID_ROOT\020\007B+\n\025sawtooth.sd" +
      "k.protobufP\001Z\020client_state_pb2b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          sawtooth.sdk.protobuf.ClientListControl.getDescriptor(),
        }, assigner);
    internal_static_ClientStateListRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ClientStateListRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientStateListRequest_descriptor,
        new java.lang.String[] { "StateRoot", "Address", "Paging", "Sorting", });
    internal_static_ClientStateListResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ClientStateListResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientStateListResponse_descriptor,
        new java.lang.String[] { "Status", "Entries", "StateRoot", "Paging", });
    internal_static_ClientStateListResponse_Entry_descriptor =
      internal_static_ClientStateListResponse_descriptor.getNestedTypes().get(0);
    internal_static_ClientStateListResponse_Entry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientStateListResponse_Entry_descriptor,
        new java.lang.String[] { "Address", "Data", });
    internal_static_ClientStateGetRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ClientStateGetRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientStateGetRequest_descriptor,
        new java.lang.String[] { "StateRoot", "Address", });
    internal_static_ClientStateGetResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ClientStateGetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientStateGetResponse_descriptor,
        new java.lang.String[] { "Status", "Value", "StateRoot", });
    sawtooth.sdk.protobuf.ClientListControl.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}