package model;

public class FlightDate extends Timer
{
   private int day;
   private int month;
   private int year;

   public FlightDate(int year, int month, int day, int hour, int minute,
         int second)
   {
      super(hour, minute, second);
      this.year = year;
      this.month = month;
      this.day = day;
   }

   public FlightDate(int year, int month, int day, Timer timer)
   {
      super(timer.getHour(), timer.getMinute(), timer.getSecond());
      this.year = year;
      this.month = month;
      this.day = day;
   }

   public FlightDate(int year, int month, int day, int seconds)
   {
      super(seconds);
      this.year = year;
      this.month = month;
      this.day = day;
   }

   public FlightDate dateNow()
   {
      return new FlightDate(this.year, this.month, this.day, super.getHour(),
            super.getMinute(), super.getSecond());
   }
   

}
