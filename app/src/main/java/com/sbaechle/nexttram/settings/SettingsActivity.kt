package com.sbaechle.nexttram.settings

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.sbaechle.nexttram.R
import com.sbaechle.nexttram.settings.searchAdapter.StationListAdapter
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by sbaechle on 25.02.2018.
 */

class SettingsActivity : AppCompatActivity () {

    var viewModel: SettingsViewModel? = null
    var searchAdapter: StationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        viewModel = SettingsViewModel.create(this)

        searchAdapter = StationListAdapter(viewModel)
        search_results.adapter = searchAdapter
        search_results.layoutManager = LinearLayoutManager(this)

        search_station.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                viewModel?.onSearchTextChanged(search_station.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewModel?.searchResult?.observe(this, Observer { searchAdapter?.swap(it) }) //TODO why can it be null?
        viewModel?.selectedStation?.observe(this, Observer { selection.text = it })
    }


}