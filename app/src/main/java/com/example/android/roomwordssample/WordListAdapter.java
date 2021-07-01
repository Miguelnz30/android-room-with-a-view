package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words
    private Context mContext;
    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if(mWords !=null){
        Word current = mWords.get(position);
        holder.setData(current.getWord(),position);
        holder.setListeners();
        }
        else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText(R.string.no_note);
        }
    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if(mWords!=null)
            return mWords.size();
        else return 0;
    }

    public Word getWordAtPosition (int position) {
        return mWords.get(position);
    }
    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private int mPosition;
        private ImageView  imgEdit;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            imgEdit 	 = itemView.findViewById(R.id.ivRowEdit);
        }
        public void setData(String note, int position) {
            wordItemView.setText(note);
            mPosition = position;
        }

        public void setListeners() {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditWordActivity.class);
                    intent.putExtra("note_id", mWords.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, MainActivity.UPDATE_WORD_ACTIVITY_REQUEST_CODE);
                }
            });
        }
    }
}


