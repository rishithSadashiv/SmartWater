package com.example.smartwater;

public class Node {

    String id, lattitude, longitude, location, drillDate, motorInstallationDate, depth, casingLength, pipeInLength, cableLength;

    public Node() {

    }

    public Node(String id, String lattitude, String longitude, String location, String drillDate, String motorInstallationDate, String depth, String casingLength, String pipeInLength, String cableLength) {
        this.id = id;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.location = location;
        this.drillDate = drillDate;
        this.motorInstallationDate = motorInstallationDate;
        this.depth = depth;
        this.casingLength = casingLength;
        this.pipeInLength = pipeInLength;
        this.cableLength = cableLength;
    }

    public String getId() {
        return id;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDrillDate() {
        return drillDate;
    }

    public String getMotorInstallationDate() {
        return motorInstallationDate;
    }

    public String getDepth() {
        return depth;
    }

    public String getCasingLength() {
        return casingLength;
    }

    public String getPipeInLength() {
        return pipeInLength;
    }

    public String getCableLength() {
        return cableLength;
    }
}
