package se.chalmers.datx02.PBFT;

public enum Error {
    SerializationError{
        public String display(){
            return "Serialization error";
        }
    },
    ServiceError{
        public String display(){
            return "Service error";
        }
    },
    SigningError{
        public String display(){
            return "Signing error";
        }
    },
    FaultyPrimary{
        public String display(){
            return "Faulty primary error";
        }
    },
    InvalidMessage{
        public String display(){
            return "Invalid message error";
        }
    },
    InternalError{
        public String display(){
            return "Internal error";
        }
    }
}
