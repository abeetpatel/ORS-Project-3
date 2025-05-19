package in.co.rays.project_3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
* data Uility class to formate data
 * @author Anshul Prajapati
*
*/
public class DataUtility {
/**
* Application time data formate
*/
public static final String APP_DATE_FORMATE="MM/dd/yyyy";
	
public static final String APP_TIME_FORMATE="MM/dd/yyyy HH:mm:ss";
	
/**
* Applicaton time data formate
*/
public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMATE);
public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMATE);

/**
* getString(String s) Trims and trailing and leading spaces of a String
*
* @param val
* @return val
*/
public static String getString(String val) {
if (DataValidator.isNotNull(val)) {
return val.trim();
} else {
return val;
}
}

/**
* Converts and Object to String
*
* @param val
* :value
* @return String
*/

public static String getStringData(Object val) {
if (val != null) {
return val.toString();
} else {
return "";
}
}

/**
*
* Converts String InTo Integer
*
* @param val
* :value
* @return int
*/

public static int getInt(String val) {
if (DataValidator.isInteger(val)) {
return Integer.parseInt(val);
} else {
return 0;
}
}

/**
*
* Converts String InTo Long
*
* @param val
* :value
* @return Long
*/

public static Long getLong(String val) {
System.out.println("........in dataUtility..........."+val);
if (DataValidator.isLong(val)) {
System.out.println("........in dataUtility"+val+",,,,,,"+Long.parseLong(val));
return Long.parseLong(val);
} else {
return (long) 0;
}
}

/**
* Convert String into Date
*
* @param val
* :value
* @return Date
*/

public static Date getDate(String val) {
System.out.println("oooooooooo"+val);
Date date = null;
try {
date = formatter.parse(val);

} catch (Exception e) {
}
System.out.println("..............pppppp"+date);
return date;
}

/**
* convert string to date
* @param date
* @return
*/
public static String getDateString(Date date) {
try {
return formatter.format(date);
} catch (Exception e) {

}
return "";

}

/**
* convert date and time
@param date
@param day
* @return
*/
public static Date getDate(Date date, int day) {
return null;
}

/**
* convert timestamp to string
* @param val
* @return
*/
public static Timestamp geTimestamp(String val) {
Timestamp timeStamp = null;
try {
timeStamp = new Timestamp(timeFormatter.parse(val).getTime());

} catch (Exception e) {
return null;
}
return timeStamp;

}

/**
* convert timestamp in to long
* @param l
* @return
*/
public static Timestamp getTimeStamp(long l) {
Timestamp timeStamp = null;
try {
timeStamp = new Timestamp(l);

} catch (Exception e) {
return null;
}
return timeStamp;
}

/**
* convert timestamp in to string
* @return Timestamp
*/
public static Timestamp getCurrentTimeStamp() {
Timestamp timeStamp = null;
try {
timeStamp = new Timestamp(new Date().getTime());
} catch (Exception e) {

}
return timeStamp;

}

/**
* convert timestamp timestamp to long
* @param Timestamp
* @return long
*/
public static long getTimestamp(Timestamp tm) {
try {
return tm.getTime();
} catch (Exception e) {
return 0;
}
}
}