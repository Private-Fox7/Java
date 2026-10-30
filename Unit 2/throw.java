class Eligible
    {
        void age(int Age)
        {
            try
                {
                    if (Age<18)
                    {
                        throw new ArithmeticException("You are not Eligible");
                        
                    }
                    else{
                        System.out.println("You are eligible");
                    }
                }
            catch (ArithmeticException e)
                {
                    System.out.print("Error occured check the logs:"+e);
                }
            catch (Exception ee)
                {
                    System.out.print("Error occured check the logs:"+ee);
                }
            finally {
                System.out.println("Thank you..");
            }
        }
    }

class Throw
    {
        public static void main(String [] args)
        {
            Eligible s = new Eligible();
            s.age(1);
        }
    }