public class Entity {
    int localID; //local ID or its position in the file
    String entityName;
    int entityTypeID;

    public Entity(int localID_, String entityName_, int entityTypeID_) {
        localID = localID_;
        entityName = entityName_;
        entityTypeID = entityTypeID_;
    }

    public int getLocalID() {
        return localID;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getEntityTypeID() {
        return entityTypeID;
    }

    public void setEntityTypeID(int entityTypeID) {
        this.entityTypeID = entityTypeID;
    }
}
