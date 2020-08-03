package com.example.alzheimers_detection;



import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.NotificationCompatSideChannelService;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alzheimers_detection.R;
import com.example.alzheimers_detection.ResponseMessage;

import java.text.Format;
import java.util.List;

//import static com.example.api_ai.R.drawable.ic_baseline_menu_book_24;
import static java.lang.String.format;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    List<ResponseMessage> responseMessages;
    Context context;
    private int textMessage;
    private int textMessage1;

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);


        }
    }

    public MessageAdapter(List<ResponseMessage> responseMessages, Context context) {
        this.responseMessages = responseMessages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessages.get(position).isMe()){
            return R.layout.me_bubble;
        }
        return R.layout.bot_bubble;
    }

    @Override
    public int getItemCount() {
        return  responseMessages.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        //holder.textView.setText(responseMessages.get(position).getText());
        String input_message=null;
        input_message=responseMessages.get(position).text;
        if(responseMessages.get(position).isMe()){
            holder.textView.setText(responseMessages.get(position).getText());

        }
        else
        {




            if(input_message.compareToIgnoreCase("hi")==0||input_message.compareToIgnoreCase("hello")==0) {
                holder.textView.setText(String.format("Hello!%s\n\nWelcome to Alzheimer's detection App\n\n0.Menu%s\n\n1.What is Alzheimer's disease?%s\n\n2.What are the causes and symptoms?%s\n\n3.How is this game helpful?%s\n\n4.What are the Myths?%s\n\n5.How many people have Alzeihmer?%s\n\n6.Are there any Services and support organizations?%s\n\n7.Are there any treatments available?%s\n\n8.How can I avoid Alzeihmer?%s\n\n9.Share with friends and family.%s\n\n10.Donate now to help millions.%s\n\nEnter Your choice:", getEmojiByUnicode(0x1F60A), getEmojiByUnicode(0x1F4CC), getEmojiByUnicode(0x1F9D0), getEmojiByUnicode(0x1F5E3), getEmojiByUnicode(0x1F4F2), getEmojiByUnicode(0x1F615), getEmojiByUnicode(0x1F4C8), getEmojiByUnicode(0x26D1), getEmojiByUnicode(0x1F489), getEmojiByUnicode(0x1F4CB), getEmojiByUnicode(0x1F4AC), getEmojiByUnicode(0x1F3E5)));
            }

            else {
                switch (input_message) {
                    case "0":
                        holder.textView.setText(format("Hello!%s\n\nWelcome to Alzheimer's detection App\n\n0.Menu%s\n\n1.What is Alzheimer's disease?%s\n\n2.What are the causes and symptoms?%s\n\n3.How is this game helpful?%s\n\n4.What are the Myths?%s\n\n5.How many people have Alzeihmer?%s\n\n6.Are there any Services and support organizations?%s\n\n7.Are there any treatments available?%s\n\n8.How can I avoid Alzeihmer?%s\n\n9.Share with friends and family.%s\n\n10.Donate now to help millions.%s\n\nEnter Your choice:", getEmojiByUnicode(0x1F604), getEmojiByUnicode(0x1F4CC), getEmojiByUnicode(0x1F9D0), getEmojiByUnicode(0x1F5E3), getEmojiByUnicode(0x1F4F2), getEmojiByUnicode(0x1F615), getEmojiByUnicode(0x1F4C8), getEmojiByUnicode(0x26D1), getEmojiByUnicode(0x1F489), getEmojiByUnicode(0x1F4CB), getEmojiByUnicode(0x1F4AC), getEmojiByUnicode(0x1F3E5)));
                        break;
                    case "1":
                        holder.textView.setText(format("%sAlzheimer’s is an irreversible brain disease that slowly destroys memory skills,\nthinking skills and eventually, the ability to carry out daily activities, \nleading to the need for full-time care.%s\n\n%sAlzheimer's disease is a type of dementia that causes problems with memory, thinking and behavior.\nSymptoms usually develop slowly and get worse over time, becoming severe enough to interfere with daily tasks.%s\n\n%sTo know more:%s\n\n%sReply 0 for Menu", getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x1F9EC),getEmojiByUnicode(0x25B6),getEmojiByUnicode(0x1F9EC), getEmojiByUnicode(0x1F4CC), "https://www.health.harvard.edu/a_to_z/alzheimers-disease-a-to-z",getEmojiByUnicode(0x1F4CD)));

                        break;
                    case "2":
                        holder.textView.setText(format("%sResearchers believe there is not a single cause of Alzheimer's disease.\nThe disease likely develops from multiple factors, such as genetics, lifestyle and environment.%s\n\n%s A major review of published research suggests that chronic stress and anxiety can damage areas of the brain involved in emotional responses, thinking and memory, leading to depression and even Alzheimer's disease\n\n%sReply 0 for Menu ", getEmojiByUnicode(0x25B6),getEmojiByUnicode(0x1F5E3) ,getEmojiByUnicode(0x25B6),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "3":
                        holder.textView.setText(format("%sPrevention is better than cure.%s\n\n%sThis is a practical 10 staged game which provides reliable results based on MOca and SAGE\n\n%sThe app by detecting early signs of Alzheimer's will not only allow you to consult help at right time but also prevent further damage\n\n%sAvailable with simple graphics,native language support and features such as list of nearby doctors,chatbot for customized advice,makes it a must-have App for your device\n\n%sReply 0 for Menu ", getEmojiByUnicode(0x1F397), getEmojiByUnicode(0x1F397), getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x25B6),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "4":
                        holder.textView.setText(format("%sMajor Myths about the disease are :-\n%sMyth No. 1: Only older people get Alzheimer’s.\n%sMyth No. 2: A diagnosis of Alzheimer’s is a death sentence.\n%sMyth No. 3: Alzheimer’s is hereditary.(Only partially true).We have taken care of that in the game.\n%sMyth No. 4: Both genders are at risk for the disease.(Although there are several theories about why the disease is more prevalent among women,\nwe don’t yet have any definitive answers).\n\n%sReply 0 for Menu", getEmojiByUnicode(0x1F6A8), getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x25B6),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "5":
                        holder.textView.setText(format("%sIt is estimated that there are approximately 44 million people worldwide living with Alzheimer's disease.\n\n%sIn India, more than 4 million people are estimated to be suffering from Alzheimer's and other forms of dementia,\ngiving the country the third highest caseload in the world.\n\n%sReply 0 for Menu", getEmojiByUnicode(0x1F4CA), getEmojiByUnicode(0x1F4CA),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "6":
                        holder.textView.setText(format("%sServices around you are:-\n\n\n%sReply 0 for Menu", getEmojiByUnicode(0x1F4CC),getEmojiByUnicode(0x1F4CD)));

                        break;
                    case "7":
                        holder.textView.setText(format("%sAs no specific cure is available we suggest you to consult a doctor for a proper treatment.But always try maintaining a healthy lifestyle.\n\n%sStay updated : \n\n%sReply 0 for Menu", getEmojiByUnicode(0x25B6), getEmojiByUnicode(0x1F4CC),getEmojiByUnicode(0x1F4CC)));
                        break;
                    case "8":
                        holder.textView.setText(format("%sHabits to prevent Alzheimer :\n\n%s1.Exercise:The recommendation is 30 minutes of moderately vigorous aerobic exercise, three to four days per week.\n\n%s2.Eat a healthy diet:The diet includes fresh vegetables and fruits, whole grains, olive oil, nuts, legumes,fish, moderate amounts of poultry, eggs, and dairy,moderate amounts of red wine and red meat only sparingly.\n\n%s3.Get enough sleep:Aim for seven to eight hours per night.\n\n%s4.Learn new things: Jigsaw puzzles, domino, playing cards, dice and word puzzles are simple games that can be easily adapted.\n\n%s5.Connect socially: Greater social contact helps prevent Alzheimer's.\n\n%sReply 0 for Menu", getEmojiByUnicode(0x1F9D8), getEmojiByUnicode(0x1F938), getEmojiByUnicode(0x1F352),getEmojiByUnicode(0x1F4A4), getEmojiByUnicode(0x1F3B2), getEmojiByUnicode(0x1F465),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "9":
                        holder.textView.setText(format("%sShare it with your friends : \n\n%sReply 0 for Menu",getEmojiByUnicode(0x1F6D1),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "10":
                        holder.textView.setText(format("%sDonate here : \n\n%sReply 0 for Menu", getEmojiByUnicode(0x1F4E2),getEmojiByUnicode(0x1F4CD)));
                        break;
                    case "Thank you":
                        holder.textView.setText(format("Glad! I was able to help%s\n\n%sReply 0 for Menu",getEmojiByUnicode(0x1F604),getEmojiByUnicode(0x1F4CD)));
                        break;

                    default :
                        if(input_message.compareToIgnoreCase("hi")!=0||input_message.compareToIgnoreCase("hello")!=0){

                            holder.textView.setText(format("Sorry I am an automated system and didn't understand your reply\n\nWelcome to Alzheimer's detection App\n\n0.Menu%s\n\n1.What is Alzheimer's disease?%s\n\n2.What are the causes and symptoms?%s\n\n3.How is this game helpful?%s\n\n4.What are the Myths?%s\n\n5.How many people have Alzeihmer?%s\n\n6.Are there any Services and support organizations?%s\n\n7.Are there any treatments available?%s\n\n8.How can I avoid Alzeihmer?%s\n\n9.Share with friends and family.%s\n\n10.Donate now to help millions.%s\n\nEnter Your choice:",  getEmojiByUnicode(0x1F4CC), getEmojiByUnicode(0x1F9D0), getEmojiByUnicode(0x1F5E3), getEmojiByUnicode(0x1F4F2), getEmojiByUnicode(0x1F615), getEmojiByUnicode(0x1F4C8), getEmojiByUnicode(0x26D1), getEmojiByUnicode(0x1F489), getEmojiByUnicode(0x1F4CB), getEmojiByUnicode(0x1F4AC), getEmojiByUnicode(0x1F3E5)));

                        }
                        break;


                }

            }







        }

    }
}

