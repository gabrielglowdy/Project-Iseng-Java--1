/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mind;

/**
 *
 * @author g4732
 */
public class ObjectInput {
    String query, result;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ObjectInput(String query, String result) {
        this.query = query;
        this.result = result;
    }

    public ObjectInput() {
    }
    
    
}
