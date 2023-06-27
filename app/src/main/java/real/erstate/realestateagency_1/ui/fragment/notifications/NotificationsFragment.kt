package real.erstate.realestateagency_1.ui.fragment.notifications

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.Favorite
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.ui.fragment.home.AdapterTwoLoad
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.room.FavDB
import real.erstate.realestateagency_1.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var favDB: FavDB
    private val viewModel : NotificationsViewModel by viewModel()
    private lateinit var adapterFav: AdapterFav
    private var favItemList: MutableList<Favorite> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.shimmer.startShimmer()
        val listLoad: ArrayList<LoadRel> = arrayListOf()
        repeat(8) {
            listLoad.add(
                LoadRel(
                    image = R.drawable.screensaver,
                    tvDil = "",
                    tvTitle = "",
                    tvSan = "",
                    tvKm = "",
                    tvLocation = "",
                    tvRoom = "",
                    id = ""
                )
            )
        }
        val adapterLoad = AdapterTwoLoad()
        adapterLoad.submitList(listLoad)
        initView()
        onViewModel()
        return binding.root
    }


    private fun onViewModel(){
        viewModel.loading.observe(requireActivity()){
            binding.shimmer.isVisible = it
        }

        viewModel.getApartment().observe(requireActivity()){
            when(it.status){
                Status.SUCCESS->{
                    viewModel.loading.postValue(false)
                    binding.con.isVisible = true
                    favDB = FavDB(requireActivity())
                    loadData()
                    val itemTouchHelper = ItemTouchHelper(simpleCallback)
                    itemTouchHelper.attachToRecyclerView(binding.item.rvH)
                }
                Status.ERROR->{
                    viewModel.loading.postValue(true)
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }
    private fun initView() {
        binding.item.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }
    @SuppressLint("Range")
    private fun loadData() {
        favItemList.clear()
        val db = favDB.readableDatabase
        val cursor = favDB.selectAllFavoriteList()
        try {
            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE))
                val id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID))
                val image = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE))
                val dil = cursor.getString(cursor.getColumnIndex(FavDB.DIL))
                val room = cursor.getString(cursor.getColumnIndex(FavDB.ROOM))
                val san = cursor.getString(cursor.getColumnIndex(FavDB.SAN))
                val local = cursor.getString(cursor.getColumnIndex(FavDB.LOCAL))
                val km = cursor.getString(cursor.getColumnIndex(FavDB.KM))
                val favItem = Favorite(
                    id,
                    image,
                    dil,
                    title,
                    san,
                    km,
                    room,
                    local
                )
                favItemList.add(favItem)
            }
        } finally {
            cursor.close()
            db?.close()
        }
        adapterFav = AdapterFav(requireActivity(), favItemList)
        binding.item.rvH.adapter = adapterFav
    }

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val favItem = favItemList[position]
            if (direction == ItemTouchHelper.LEFT) {
                favItemList.removeAt(position)
                adapterFav.submitList(favItemList.toMutableList())
                adapterFav.notifyItemRemoved(position)
                adapterFav.notifyItemRangeChanged(position, adapterFav.itemCount)
                favDB.removeFav(favItem.id)
            }
        }
    }
}