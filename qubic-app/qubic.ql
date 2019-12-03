kmh=GetArgs(0);

if(kmh <= 10){
    traffic = 'stau';
} else{
    traffic = 'normal';
}

return(traffic);