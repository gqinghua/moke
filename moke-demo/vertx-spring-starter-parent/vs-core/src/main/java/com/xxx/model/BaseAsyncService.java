package com.xxx.model;

/**
 * @author
 * @date
 */
public interface BaseAsyncService {

    default String getAddress() {
        String className = this.getClass().getName();
        return className.substring(0, className.lastIndexOf("Impl")).replace(".impl", "");
    }

    default Class getAsyncInterfaceClass() throws ClassNotFoundException {
        String className = this.getClass().getName();
        return Class.forName(className.substring(0, className.lastIndexOf("Impl")).replace(".impl", ""));
    }
}
