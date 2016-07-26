package com.dachen.medicine.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.dachen.medicine.R;
import com.dachen.medicine.adapter.BaseCustomAdapter.BaseViewHolder;
import com.dachen.medicine.common.utils.ToastUtils;
import com.dachen.medicine.entity.DepartmentEntity;
import com.dachen.medicine.entity.MedicineEntity;
import com.dachen.medicine.net.CustomImagerLoader;
import com.dachen.medicine.view.AddAndSubView;

public class ChoiceMedicineChildrenadapter extends BaseAdapter {
	AddAndSubView addAndSubView1;
	Context context;
	public List<MedicineEntity> list;
	public List<DepartmentEntity> parentlist;
	public HashMap<String, MedicineEntity> choicedList;
	ChoiceItemNumInterface numChoices;
	int number;
	protected LayoutInflater mInflater;
	int mChildItemLayoutId;
	int pareid;

	public ChoiceMedicineChildrenadapter(Context context, int pareid,
			int resId, List<MedicineEntity> objects,
			List<DepartmentEntity> parentlist,
			HashMap<String, MedicineEntity> choicedList,
			ChoiceItemNumInterface numChoice) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		list = objects;
		numChoices = numChoice;
		this.choicedList = choicedList;
		mChildItemLayoutId = resId;
		this.pareid = pareid;
		this.parentlist = parentlist;
		// TODO Auto-generated constructor stub
	}

	protected BaseViewHolder getViewHolderChildren() {
		// TODO Auto-generated method stub
		return new ViewHolder();
	}

	protected BaseViewHolder getViewHolderParent() {
		// TODO Auto-generated method stub
		return new ViewHolderParent();
	}

	protected void fillValues(
			com.dachen.medicine.adapter.BaseCustomAdapter.BaseViewHolder baseViewHolder,
			final int position) {
		// TODO Auto-generated method stub
		if (position < parentlist.size()) {
			final ViewHolderParent holder = (ViewHolderParent) baseViewHolder;
			final DepartmentEntity department = (DepartmentEntity) getItem(position);
			holder.name.setText(department.getName());

		} else {
			final ViewHolder holder = (ViewHolder) baseViewHolder;
			final MedicineEntity media = (MedicineEntity) getItem(position);
			holder.tv_medicine_name.setText(media.name);
			String url = media.url;
			holder.tv_medicine_height.setText(media.height);
			holder.tv_company.setText(media.company);

			if (!TextUtils.isEmpty(url)) {
				CustomImagerLoader.getInstance().loadImage(holder.img_listview,
						url);
			} else {
				holder.img_listview
						.setImageResource(R.drawable.image_download_fail_icon);

			}
			if (null != holder.ll_numshow) {
				holder.subview_add.setViewsLayoutParm(120, 50);
				holder.subview_add.setButtonLayoutParm(30, 50);
				holder.subview_add.setEditTextLayoutHeight(50);
				holder.subview_add.setButtonBgResource(R.drawable.addpress,
						R.drawable.subtract);
				holder.subview_add.setTextViewBgResource(R.drawable.imports);

			}
			holder.btn_choice_medicine
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							numChoices.isShow(true);
							number = holder.subview_add.getNum();
							ToastUtils.showToast("position====" + position);
							MedicineEntity entity = (MedicineEntity) choicedList
									.get(list.get(position - parentlist.size()).name);
							if (null != entity) {

								if (entity.num >= 0) {
									entity.num = (number + entity.num);
									choicedList.put(entity.name, entity);
								}

							} else {
								entity = list.get(position - parentlist.size());
								entity.time = (com.dachen.medicine.common.utils.TimeUtils
										.getNowTime());
								choicedList.put(entity.name, entity);
							}
							numChoices.getList(choicedList);

						}
					});
		}

	}

	public static class ViewHolder extends BaseViewHolder {
		@Bind(R.id.img_listview)
		ImageView img_listview;
		@Bind(R.id.tv_medicine_name)
		TextView tv_medicine_name;
		@Bind(R.id.tv_medicine_height)
		TextView tv_medicine_height;
		@Bind(R.id.tv_company)
		TextView tv_company;
		@Nullable
		@Bind(R.id.ll_numshow)
		LinearLayout ll_numshow;
		@Bind(R.id.subview_add)
		AddAndSubView subview_add;
		@Bind(R.id.btn_choice_medicine)
		Button btn_choice_medicine;
	}

	public static class ViewHolderParent extends BaseViewHolder {
		@Bind(R.id.tv_name)
		TextView name;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < parentlist.size()) {
			return createViewFromResource(position, convertView, parent, pareid);
		} else {
			return createViewFromResource(position, convertView, parent,
					mChildItemLayoutId);
		}

	}

	/**
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param resource
	 * @return
	 */
	protected View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {

		BaseViewHolder holder = null;
		View v = convertView;

		// if (v == null) {
		v = mInflater.inflate(resource, parent, false);
		if (position < parentlist.size()) {
			holder = getViewHolderParent();
		} else {
			holder = getViewHolderChildren();
		}

		assert v != null;
		// initialize(holder, v);
		ButterKnife.bind(holder, v);
		v.setTag(holder);
		/*
		 * } else { holder = (BaseViewHolder) v.getTag(); }
		 */
		fillValues(holder, position);

		return v;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() + parentlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 < parentlist.size()) {
			return parentlist.get(arg0);
		} else {
			return list.get(arg0 - parentlist.size());
		}

	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
