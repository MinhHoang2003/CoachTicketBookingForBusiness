package com.example.coachticketbookingforbusiness.dialog.choose_coach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.CoachAdapter
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.coach.CoachRepository
import com.example.coachticketbookingforbusiness.repository.coach.ICoachRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_choose_coach.*
import kotlinx.android.synthetic.main.layout_choose_coach.view.*


class DialogChooseCoach : DialogFragment() {

    private val mCoachAdapter = CoachAdapter()
    var disposable = CompositeDisposable()
    var coachSelectedListener: ((String) -> Unit)? = null
    private val mCoachRepository: ICoachRepository by lazy {
        CoachRepository.getInstance(RetrofitClient.getAPIService())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_choose_coach, container, false)
        view.toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        view.toolbar.title = "Chọn xe cho tuyến"
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Material_Light_NoActionBar_TranslucentDecor
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            dismiss()
        }
        recyclerCoach.apply {
            adapter = mCoachAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initListener()
        getCoach()
    }

    private fun getCoach() {
        mCoachRepository.getCoach()
            .applyScheduler()
            .subscribe { coach, err ->
                if (err == null) {
                    mCoachAdapter.setData(coach)
                }
            }.addToCompositeDisposable(disposable)

    }

    private fun initListener() {
        mCoachAdapter.onClickCoachListener = {
            coachSelectedListener?.invoke(it)
            dismiss()
        }
    }

    override fun onDetach() {
        disposable.clear()
        disposable.dispose()
        super.onDetach()
    }
}