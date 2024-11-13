package aed.nodos;

public class Traslado {
    int _id;
    int _origen;
    int _destino;
    int _gananciaNeta;
    int _timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this._id = id;
        this._origen = origen;
        this._destino = destino;
        this._gananciaNeta = gananciaNeta;
        this._timestamp = timestamp;
    }

    public int getDestino() {
        return _destino;
    }
    public int getGananciaNeta() {
        return _gananciaNeta;
    }
    public int getId() {
        return _id;
    }
    public int getOrigen() {
        return _origen;
    }
    public int getTimestamp() {
        return _timestamp;
    }
}
