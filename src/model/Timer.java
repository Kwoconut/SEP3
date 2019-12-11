package model;

import java.io.Serializable;

public class Timer implements Serializable
{

   private int Second;
   private int Minute;
   private int Hour;

   public Timer(int hour, int minute, int second)
   {
      setTimer(hour, minute, second);
   }

   public Timer(int second)
   {
      this.Hour = second / 3600;
      this.Minute = (second % 3600) / 60;
      this.Second = (second % 3600) % 60;
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
         this.Hour = 23;
      }
      else if (hour < 0)
      {
         this.Hour = 0;
      }
      else
      {
         this.Hour = hour;
      }
   }

   public void setMinute(int minute)
   {
      if (minute > 59)
      {
         this.Minute = 59;
      }
      else if (minute < 0)
      {
         this.Minute = 0;
      }
      else
      {
         this.Minute = minute;
      }
   }

   public void setSecond(int second)
   {
      if (second > 59)
      {
         this.Second = 59;
      }
      else if (second < 0)
      {
         this.Second = 0;
      }
      else
      {
         this.Second = second;
      }
   }

   public void increment()
   {
      this.Second++;
      if (this.Second == 60)
      {
         this.Second = 0;
         this.Minute++;
      }

      if (this.Minute == 60)
      {
         this.Minute = 0;
         this.Hour++;
      }

      if (this.Hour == 24)
      {
         this.Hour = 0;
      }
   }

   public void decrement()
   {
      this.Second--;

      if (this.Second == -1)
      {
         this.Second = 59;
         this.Minute--;
      }

      if (this.Minute == -1)
      {
         this.Minute = 59;
         this.Hour--;
      }

      if (this.Hour == -1)
      {
         this.Hour = 23;
      }

   }

   public int getHour()
   {
      return Hour;
   }

   public int getMinute()
   {
      return Minute;
   }

   public int getSecond()
   {
      return Second;
   }

   public Timer timeNow()
   {
      return new Timer(this.Second, this.Minute, this.Hour);
   }

   public int convertToSeconds()
   {
      return this.Hour * 60 * 60 + this.Minute * 60
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

      if (this.Hour < 10)
      {
         s += "0" + this.Hour + ":";
      }
      else
      {
         s += this.Hour + ":";
      }

      if (this.Minute < 10)
      {
         s += "0" + this.Minute + ":";
      }
      else
      {
         s += this.Minute + ":";
      }

      if (this.Second < 10)
      {
         s += "0" + this.Second;
      }
      else
      {
         s += this.Second;
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
      return this.Second == other.getSecond()
            && this.Minute == other.getMinute() && this.Hour == other.getHour();
   }

}
