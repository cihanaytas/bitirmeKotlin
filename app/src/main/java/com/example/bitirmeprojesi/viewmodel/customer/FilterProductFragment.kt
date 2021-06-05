package com.example.bitirmeprojesi.viewmodel.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import kotlinx.android.synthetic.main.fragment_filter_product.*
import org.florescu.android.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener


class FilterProductFragment : Fragment(), AdapterView.OnItemClickListener{
    private lateinit var viewModel : CustomerUrunlerViewModel
    var min=0
    var max=0
    var mylist = arrayListOf<String>()
    private var listView:ListView? = null
    private var arrayAdapter:ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)

        //*******************
        listView = lsw
        arrayAdapter = context?.let {
            ArrayAdapter(it,android.R.layout.simple_list_item_multiple_choice
                    ,resources.getStringArray(R.array.categoryName))
        }
        listView?.adapter = arrayAdapter
        listView?.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView?.onItemClickListener = this
        //*******************


        rangeSeekBar.setOnRangeSeekBarChangeListener { bar, number, number2 ->
            min = number as Int
            max = number2 as Int
           textMinMax.text = ("Min: " + number + "₺ Max: " + number2 + "₺").toString()
        }

        //var qwe: Array<String> = mylist.toTypedArray()

        buttonfilter.setOnClickListener {
            //requireFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss()


            if(mylist.isEmpty() && min==0 && max==0){
                viewModel.urunleriAl(0, mylist.toTypedArray()!!,min.toDouble(),max.toDouble())
                val action = FilterProductFragmentDirections.actionFilterProductFragmentToUrunlerFragment(0)
                Navigation.findNavController(it).navigate(action)
            }

            else{
                viewModel.urunleriAl(0, mylist.toTypedArray()!!,min.toDouble(),max.toDouble())
                val action = FilterProductFragmentDirections.actionFilterProductFragmentToUrunlerFragment(0,true)
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items:String = parent?.getItemAtPosition(position) as String
        if(mylist.contains(items)){
            mylist.remove(items)
        }
        else{
            mylist.add(items)
        }
      //  println(mylist)
    }


}