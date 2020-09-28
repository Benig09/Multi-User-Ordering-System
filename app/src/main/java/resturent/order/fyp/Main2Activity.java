package resturent.order.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Main2Activity extends AppCompatActivity implements
        CategoryViewFragment.FragmentAListener,
        CategoryViewFragmentReverc.FragmentBListener,
        IteamViewFragment.FragmentDListener,
        IteamViewFragmentReverc.FragmentDListener,
        IteamDetilesViewFragment.FragmentDListener,
        IteamDetilesViewFragmentReverc.FragmentDListener,
        PaymentViewFragment.FragmentCListener,
        PaymentViewFragmentReverc.FragmentCListener,
        CartViewFragment.FragmentCListener,
        CartViewFragmentReverc.FragmentCListener{

    private CategoryViewFragment fragmentA;
    private CategoryViewFragmentReverc fragmentA2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        fragmentA = new CategoryViewFragment();
        fragmentA2 = new CategoryViewFragmentReverc();




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentA)
                .replace(R.id.container_b, fragmentA2)
                .commit();



    }

    @Override
    public void onInputASent(String input) {



        if (input == "1"){

            Fragment newFragment = new CartViewFragment();

            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            newFragment.setArguments(args);
            transaction.commit();

        }else {

            Fragment newFragment = new IteamViewFragment();
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            newFragment.setArguments(args);
            transaction.commit();
        }






    }






    @Override
    public void onInputBSent(CharSequence input) {


            if (input == "2"){
                Fragment newFragment = new CartViewFragmentReverc();

                Bundle args = new Bundle();
                args.putCharSequence("index", input);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_b, newFragment);
                transaction.addToBackStack(null);
                newFragment.setArguments(args);
                transaction.commit();


            }else {


                Fragment newFragment = new IteamViewFragmentReverc();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_b, newFragment);
                transaction.addToBackStack(null);
                Bundle args = new Bundle();
                args.putCharSequence("index", input);

                newFragment.setArguments(args);
                transaction.commit();
            }







    }

    public void onInputCSent(String input) {

        String delimiter = "--";

        String[] parts = input.split(delimiter);

        String indecatero = parts[0];


        if (input == "4") {

           Fragment newFragment = new CategoryViewFragment();
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.container_a, newFragment);
           transaction.addToBackStack(null);
           Bundle args = new Bundle();
           args.putCharSequence("index", input);

           newFragment.setArguments(args);
           transaction.commit();

       }

       else if (input == "5") {

           Fragment newFragment = new CategoryViewFragmentReverc();
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.container_b, newFragment);
           transaction.addToBackStack(null);
           Bundle args = new Bundle();
           args.putCharSequence("index", input);

           newFragment.setArguments(args);
           transaction.commit();

       }

       else if (input == "6") {

           Fragment newFragment = new CategoryViewFragment();
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.container_a, newFragment);
           transaction.addToBackStack(null);
           Bundle args = new Bundle();
           args.putCharSequence("index", input);

           newFragment.setArguments(args);
           transaction.commit();

       }

       else if (input == "7") {

           Fragment newFragment = new CategoryViewFragmentReverc();
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.container_b, newFragment);
           transaction.addToBackStack(null);
           Bundle args = new Bundle();
           args.putCharSequence("index", input);

           newFragment.setArguments(args);
           transaction.commit();

       }else if (input == "8") {

            Fragment newFragment = new CartViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input == "9") {

            Fragment newFragment = new CartViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (indecatero.equals("1")){

            String input2 = parts[1];
            Fragment newFragment = new PaymentViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input2);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (indecatero.equals("2")){

            String input2 = parts[1];
            Fragment newFragment = new PaymentViewFragmentReverc();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_b, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input2);

            newFragment.setArguments(args);
            transaction.commit();

        }


    }

    @Override
    public void onInputDSent(String input) {

        String delimiter = "--";

         String[] parts = input.split(delimiter);

         String indecatero = parts[0];

        Log.e("fdd: ",indecatero );

        if (indecatero.equals("IteamDetiles")){


            Fragment newFragment = new IteamDetilesViewFragment();

            Bundle args = new Bundle();
            args.putString("index", input);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            newFragment.setArguments(args);
            transaction.commit();

        }else if(indecatero.equals("IteamDetilesReverc")) {

            Fragment newFragment = new IteamDetilesViewFragmentReverc();  // TOdO Change tp reverc

            Bundle args = new Bundle();
            args.putString("index", input);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_b, newFragment);
            transaction.addToBackStack(null);
            newFragment.setArguments(args);
            transaction.commit();


        } else if (input.equals("2")){

            Fragment newFragment = new CartViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input.equals("3")){

            Fragment newFragment = new CategoryViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input.equals("4")){

            Fragment newFragment = new CategoryViewFragmentReverc();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_b, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input.equals("5")){

            Fragment newFragment = new CartViewFragmentReverc();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_b, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input.equals("6")){

            Fragment newFragment = new IteamViewFragmentReverc();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_b, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }else if (input.equals("7")){

            Fragment newFragment = new IteamViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_a, newFragment);
            transaction.addToBackStack(null);
            Bundle args = new Bundle();
            args.putCharSequence("index", input);

            newFragment.setArguments(args);
            transaction.commit();

        }






    }


}