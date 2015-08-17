/*   Copyright (C) 2013-2014 Computer Sciences Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

package ezbake.common.time;



import ezbake.base.thrift.Date;
import ezbake.base.thrift.DateTime;
import ezbake.base.thrift.Time;

import java.util.Calendar;

/**
 * Created by jpercivall on 7/16/14.
 */
public final class DateUtils {

    public static Time getCurrentTime() 
    {
        Calendar calendar = Calendar.getInstance();        
        return getTimeFromCalendar(calendar);       
    }

    public static Date getCurrentDate() 
    {
        Calendar calendar = Calendar.getInstance();
        return getDateFromCalendar(calendar);
    }

    public static DateTime getCurrentDateTime() {
        DateTime currDateTime= new DateTime();
        currDateTime.setDate(getCurrentDate());
        currDateTime.setTime(getCurrentTime());
        return currDateTime;
    }
    
    
    public static DateTime getDateTimeFromUnixEpoch(long epoch) 
    {        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epoch);
        
        DateTime dt = new DateTime();
        dt.setDate(getDateFromCalendar(calendar));
        dt.setTime(getTimeFromCalendar(calendar));
        
        DateTime currDateTime= new DateTime();
        currDateTime.setDate(getCurrentDate());
        currDateTime.setTime(getCurrentTime());
        return currDateTime;
    }

    private static Date getDateFromCalendar(Calendar calendar)
    {
        Date date = new Date();        
        date.setDay((short) calendar.get(Calendar.DAY_OF_MONTH));
        date.setMonth((short) (calendar.get(Calendar.MONTH)+1));
        date.setYear((short) calendar.get(Calendar.YEAR));
        return date;
    }

    private static Time getTimeFromCalendar(Calendar calendar)
    {
        Time currTime = new Time();        

        // get calendar parts
        int offsetMinutes = (calendar.getTimeZone().getOffset(calendar.getTimeInMillis())) / (1000 * 60);

        currTime.setMillisecond((short) calendar.get(Calendar.MILLISECOND));
        currTime.setSecond((short) calendar.get(Calendar.SECOND));
        currTime.setMinute((short) calendar.get(Calendar.MINUTE));
        currTime.setHour((short) calendar.get(Calendar.HOUR_OF_DAY));

        boolean afterUtc = offsetMinutes > 0;
        offsetMinutes = Math.abs(offsetMinutes);
        final ezbake.base.thrift.TimeZone tz = new ezbake.base.thrift.TimeZone((short) (offsetMinutes / 60), (short) (offsetMinutes % 60), afterUtc);
        currTime.setTz(tz);

        return currTime;        
    }
    
    
}
