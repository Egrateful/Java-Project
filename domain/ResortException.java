/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.domain;

/**
 *
 * @author PattyTai
 */
public class ResortException extends Exception {

    /**
     * Creates a new instance of <code>ResortException</code> without detail
     * message.
     */
    public ResortException() {
    }

    /**
     * Constructs an instance of <code>ResortException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ResortException(String msg) {
        super(msg);
    }

    public ResortException(String message, Throwable cause) {
        super(message, cause);
    }
}
