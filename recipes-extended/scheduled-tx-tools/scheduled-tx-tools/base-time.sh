i=`date +%s%N`
j=`echo "37000000000 + 20000000000" | bc`
k=`echo "$i + $j" | bc`
m=`echo "$k % 1000000000" | bc`
BASE_TIME=`echo "$k - $m" | bc`

echo "Base time: $BASE_TIME"
