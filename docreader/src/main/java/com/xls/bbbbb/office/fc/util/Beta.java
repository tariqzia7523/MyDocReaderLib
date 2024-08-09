package com.xls.bbbbb.office.fc.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Signifies that a public API (public class, method or field) is subject to
 * incompatible changes, or even removal, in a future release. An API bearing
 * this annotation is exempt from any compatibility guarantees made by its
 * containing library.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Beta {
    
}
