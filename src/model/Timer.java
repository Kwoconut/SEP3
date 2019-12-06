package model;

import java.io.Serializable;

public class Timer implements Serializable
{

   private int second;
   private int minute;
   private int hour;

   public Timer(int hour, int minute, int second)
   {
      setTimer(hour, minute, second);
   }

   public Timer(int second)
   {
      this.hour = second / 3600;
      this.minute = (second % 3600) / 60;
      this.second = (second % 3600) % 60;
   }

   public void setTimer(int hour, int minute, int second)
   {
      setHour(hour);
      setMinute(minute);
      setSecond(second);
   }

   public void setTimer(Timer timer)
   {
      setHour(timer.getHour());
      setMinute(timer.getMinute());
      setMinute(timer.getSecond());
   }

   public void setHour(int hour)
   {
      if (hour > 23)
      {
         this.hour = 23;
      }
      else if (hour < 0)
      {
         this.hour = 0;
      }
      else
      {
         this.hour = hour;
      }
   }

   public void setMinute(int minute)
   {
      if (minute > 59)
      {
         this.minute = 59;
      }
      else if (minute < 0)
      {
         this.minute = 0;
      }
      else
      {
         this.minute = minute;
      }
   }

   public void setSecond(int second)
   {
      if (second > 59)
      {
         this.second = 59;
      }
      else if (second < 0)
      {
         this.second = 0;
      }
      else
      {
         this.second = second;
      }
   }

   public void increment()
   {
      this.second++;
      if (this.second == 60)
      {
         this.second = 0;
         this.minute++;
      }

      if (this.minute == 60)
      {
         this.minute = 0;
         this.hour++;
      }

      if (this.hour == 24)
      {
         this.hour = 0;
      }
   }

   public void decrement()
   {
      this.second--;

      if (this.second == -1)
      {
         this.second = 59;
         this.minute--;
      }

      if (this.minute == -1)
      {
         this.minute = 59;
         this.hour--;
      }

      if (this.hour == -1)
      {
         this.hour = 23;
      }

   }

   public int getHour()
   {
      return hour;
   }

   public int getMinute()
   {
      return minute;
   }

   public int getSecond()
   {
      return second;
   }

   public Timer timeNow()
   {
      return new Timer(this.second, this.minute, this.hour);
   }

   public int convertToSeconds()
   {
      return this.hour * 60 * 60 + this.minute * 60
            + this.getSecond();
   }

   public boolean isBefore(Timer timer)
   {
      return this.convertToSeconds() < timer.convertToSeconds();
   }

   public boolean isAfter(Timer timer)
   {
      return this.convertToSeconds() > timer.convertToSeconds();
   }

   public Timer calculateDifference(Timer timer)
   {
      int seconds = 0;
      if (this.isAfter(timer))
      {
         seconds = this.convertToSeconds() - timer.convertToSeconds();
      }
      else if (this.isBefore(timer))
      {
         seconds = timer.convertToSeconds() - this.convertToSeconds();
      }
      else if (this.equals(timer))
      {
         seconds = 0;
      }
      return new Timer(seconds);
   }

   public String toString()
   {
      String s = "";

      if (this.hour < 10)
      {
         s += "0" + this.hour + ":";
      }
      else
      {
         s += this.hour + ":";
      }

      if (this.minute < 10)
      {
         s += "0" + this.minute + ":";
      }
      else
      {
         s += this.minute + ":";
      }

      if (this.second < 10)
      {
         s += "0" + this.second;
      }
      else
      {
         s += this.second;
      }

      return s;

   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof Timer))
      {
         return false;
      }
      Timer other = (Timer) obj;
      return this.second == other.getSecond()
            && this.minute == other.getMinute() && this.hour == other.getHour();
   }

}
