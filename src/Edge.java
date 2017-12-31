public class Edge {
    int srcId;
    int dstId;
    int edgeTypeId;

    public Edge(int srcId_, int dstId_, int edgeTypeId_) {
        srcId = srcId_;
        dstId = dstId_;
        edgeTypeId = edgeTypeId_;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDstId() {
        return dstId;
    }

    public void setDstId(int dstId) {
        this.dstId = dstId;
    }

    public int getEdgeTypeId() {
        return edgeTypeId;
    }

    public void setEdgeTypeId(int edgeTypeId) {
        this.edgeTypeId = edgeTypeId;
    }
}
