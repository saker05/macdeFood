package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.fragments.AddressFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Fragment extends Fragment {

    public static final String TAG = AddressFragment.class.getSimpleName();
    public Add_Fragment() {
        // Required empty public constructor
    }
    public static Add_Fragment newInstance() {
        return new Add_Fragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_address, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//          ButterKnife.bind(this, view);
    //    mToolbar=(Toolbar)getActivity().findViewById(R.id.addressToolbar);
        //  mcategoryslider.getProgressView().setVisibility(View.GONE);

      //  setUpToolbar();
        // setUpViews();
    }



}
