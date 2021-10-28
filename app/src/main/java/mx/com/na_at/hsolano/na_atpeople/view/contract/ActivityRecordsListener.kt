package mx.com.na_at.hsolano.na_atpeople.view.contract

import mx.com.na_at.hsolano.na_atpeople.model.network.response.Activity

interface ActivityRecordsListener {
    fun onClickActivityRecordsDelete(nameProjectRecord: String, idActivityRecord: String)
    fun onClickActivityRecordsModify(activities: List<Activity>)

}