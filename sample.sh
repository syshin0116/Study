#!/bin/bash

sijak(){
echo "
                        ____   ___  ____ ___ _       _
                       / ___| / _ \/ ___|_ _| |     / |
                       \___ \| | | \___ \| || |     | |
                        ___) | |_| |___) | || |___  | |
                       |____/ \___/|____/___|_____| |_|
                           _  _____  _    __  ____  __
                          / \|_   _|/ \   \ \/ /\ \/ /
                         / _ \ | | / _ \   \  /  \  /
                        / ___ \| |/ ___ \  /  \  /  \
                       /_/   \_\_/_/   \_\/_/\_\/_/\_\
                                                                            
                                               202100171 SJS
                                                                            
                                                                             "
echo "                    "^[[44m"    JOIN    "^[[0m"            "^[[44m"   SIGN IN  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                    "^[[44m"    EXIT    "^[[0m"            "^[[44m"  SIGN OUT  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=100
}
join(){
echo "
                        ____   ___  ____ ___ _       _
                       / ___| / _ \/ ___|_ _| |     / |
                       \___ \| | | \___ \| || |     | |
                        ___) | |_| |___) | || |___  | |
                       |____/ \___/|____/___|_____| |_|
                           _  _____  _    __  ____  __
                          / \|_   _|/ \   \ \/ /\ \/ /
                         / _ \ | | / _ \   \  /  \  /
                        / ___ \| |/ ___ \  /  \  /  \
                       /_/   \_\_/_/   \_\/_/\_\/_/\_\
                                                                            
                                               202100171 SJS
                                                                            
                                                                             "
echo "                    "^[[41m"    JOIN    "^[[0m"            "^[[44m"   SIGN IN  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                    "^[[44m"    EXIT    "^[[0m"            "^[[44m"  SIGN OUT  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=101
}
signin(){
echo "
                        ____   ___  ____ ___ _       _
                       / ___| / _ \/ ___|_ _| |     / |
                       \___ \| | | \___ \| || |     | |
                        ___) | |_| |___) | || |___  | |
                       |____/ \___/|____/___|_____| |_|
                           _  _____  _    __  ____  __
                          / \|_   _|/ \   \ \/ /\ \/ /
                         / _ \ | | / _ \   \  /  \  /
                        / ___ \| |/ ___ \  /  \  /  \
                       /_/   \_\_/_/   \_\/_/\_\/_/\_\
                                                                            
                                               202100171 SJS
                                                                            
                                                                             "
echo "                    "^[[44m"    JOIN    "^[[0m"            "^[[41m"   SIGN IN  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                    "^[[44m"    EXIT    "^[[0m"            "^[[44m"  SIGN OUT  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=102
}
ex1t(){
echo "
                        ____   ___  ____ ___ _       _
                       / ___| / _ \/ ___|_ _| |     / |
                       \___ \| | | \___ \| || |     | |
                        ___) | |_| |___) | || |___  | |
                       |____/ \___/|____/___|_____| |_|
                           _  _____  _    __  ____  __
                          / \|_   _|/ \   \ \/ /\ \/ /
                         / _ \ | | / _ \   \  /  \  /
                        / ___ \| |/ ___ \  /  \  /  \
                       /_/   \_\_/_/   \_\/_/\_\/_/\_\
                                                                            
                                               202100171 SJS
                                                                            
                                                                             "
echo "                    "^[[44m"    JOIN    "^[[0m"            "^[[44m"   SIGN IN  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                    "^[[41m"    EXIT    "^[[0m"            "^[[44m"  SIGN OUT  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=103
}
signout(){
echo "
                        ____   ___  ____ ___ _       _
                       / ___| / _ \/ ___|_ _| |     / |
                       \___ \| | | \___ \| || |     | |
                        ___) | |_| |___) | || |___  | |
                       |____/ \___/|____/___|_____| |_|
                           _  _____  _    __  ____  __
                          / \|_   _|/ \   \ \/ /\ \/ /
                         / _ \ | | / _ \   \  /  \  /
                        / ___ \| |/ ___ \  /  \  /  \
                       /_/   \_\_/_/   \_\/_/\_\/_/\_\
                                                                            
                                               202100171 SJS
                                                                            
                                                                             "
echo "                    "^[[44m"    JOIN    "^[[0m"            "^[[44m"   SIGN IN  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                    "^[[44m"    EXIT    "^[[0m"            "^[[41m"  SIGN OUT  "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=104
}
picture(){
    if [ ${output} -eq 101 ] ; then
        join
    elif [ ${output} -eq 102 ] ; then
        signin
    elif [ ${output} -eq 103 ] ; then
        ex1t
    elif [ ${output} -eq 104 ] ; then
        signout
    fi
}
membershipstart(){
echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|

                                                                            
                                                                           "
echo "                 "^[[44m"           ID           "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[44m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=105
}
membershipid(){
echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|

                                                                            
                                                                           "
echo "                 "^[[41m"           ID           "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[44m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=106
}
membershipdup(){
echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|


                                                                           "
echo "                 "^[[44m"           ID           "^[[0m"   "^[[41m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[44m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=107
}
membershippw(){
echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|


                                                                           "
echo "                 "^[[44m"           ID           "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[41m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[44m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=108
}
membershipsignin(){

echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|


                                                                           "
echo "                 "^[[44m"           ID           "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[41m"  SIGN IN  "^[[0m"     "^[[44m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=109
}
membershipexit(){

echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|


                                                                           "
echo "                 "^[[44m"           ID           "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[41m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

output=110
}
membershippicture(){
if [ ${output} -eq 105 ] ; then
    membershipstart
elif [ ${output} -eq 106 ] ; then
    membershipid
elif [ ${output} -eq 107 ] ; then
    membershipdup
elif [ ${output} -eq 108 ] ; then
    membershippw
elif [ ${output} -eq 109 ] ; then
    membershipsignin
elif [ ${output} -eq 110 ] ; then
    membershipexit
fi
}
######################    start   ######################
sijak

output=101

while :
do
    read -s -n 1 input
    if [ "${input}" = "^[" ] ; then
        read -s -n 1 input
        if [ "${input}" = "[" ] ; then
            read -s -n 1 input
#join
            if [ ${output} -eq 101 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=101
                    picture
                elif [ "${input}" = "B" ] ; then
                    output=103
                    picture
                elif [ "${input}" = "C" ] ; then
                    output=102
                    picture
                elif [ "${input}" = "D" ] ; then
                    output=101
                    picture
                fi
#signin
            elif [ ${output} -eq 102 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=102
                    picture
                elif [ "${input}" = "B" ] ; then
                    output=104
                    picture
                elif [ "${input}" = "C" ] ; then
                    output=102
                    picture
                elif [ "${input}" = "D" ] ; then
                    output=101
                    picture
                fi
#ex1t
            elif [ ${output} -eq 103 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=101
                    picture
                elif [ "${input}" = "B" ] ; then
                    output=103
                    picture
                elif [ "${input}" = "C" ] ; then
                    output=104
                    picture
                elif [ "${input}" = "D" ] ; then
                    output=103
                    picture
                fi
#signout
            elif [ ${output} -eq 104 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=102
                    picture
                elif [ "${input}" = "B" ] ; then
                    output=104
                    picture
                elif [ "${input}" = "C" ] ; then
                    output=104
                    picture
                elif [ "${input}" = "D" ] ; then
                    output=103
                    picture
                fi
            fi
######################    membership    ######################

#membershipid
            if [ ${output} -eq 106 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=106
                    membershippicture
                elif [ "${input}" = "B" ] ; then
                    output=108
                    membershippicture
                elif [ "${input}" = "C" ] ; then
                    output=107
                    membershippicture
                elif [ "${input}" = "D" ] ; then
                    output=106
                    membershippicture
                fi
#membershipdup
            elif [ ${output} -eq 107 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=107
                    membershippicture
                elif [ "${input}" = "B" ] ; then
                    output=108
                    membershippicture
                elif [ "${input}" = "C" ] ; then
                    output=107
                    membershippicture
                elif [ "${input}" = "D" ] ; then
                    output=106
                    membershippicture
                fi
#membershippw
            elif [ ${output} -eq 108 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=106
                    membershippicture
                elif [ "${input}" = "B" ] ; then
                    output=109
                    membershippicture
                elif [ "${input}" = "C" ] ; then
                    output=108
                    membershippicture
                elif [ "${input}" = "D" ] ; then
                    output=108
                    membershippicture
                fi
#membershipsignin
            elif [ ${output} -eq 109 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=108
                    membershippicture
                elif [ "${input}" = "B" ] ; then
                    output=109
                    membershippicture
                elif [ "${input}" = "C" ] ; then
                    output=110
                    membershippicture
                elif [ "${input}" = "D" ] ; then
                    output=109
                    membershippicture
                fi
#membershipexit
            elif [ ${output} -eq 110 ] ; then
                if [ "${input}" = "A" ] ; then
                    output=108
                    membershippicture
                elif [ "${input}" = "B" ] ; then
                    output=110
                    membershippicture
                elif [ "${input}" = "C" ] ; then
                    output=110
                    membershippicture
                elif [ "${input}" = "D" ] ; then
                    output=109
                    membershippicture
                fi
            fi
        fi
    fi
    if [[ ${output} -eq 103 ]] ; then
        if [[ "${input}" == "" ]] ; then
            exit
        fi
    elif [[ ${output} -eq 102 ]] ; then
        if [[ "${input}" == "" ]] ; then
            membershipstart
            output=106
        fi
#   elif [[ ${output} -eq 106 ]] ; then
#       if [[ "${input}" == "" ]] ; then

#       fi
    elif [[ ${output} -eq 110 ]] ; then
        if [[ "${input}" == "" ]] ; then
            exit
        fi
#membershipsignin
    elif [[ ${output} -eq 109 ]] ; then
        if [[ "${input}" == "" ]] ; then
            echo "id = ${id}" > [membership].txt
            echo "pw = ${pw}" > [membership].txt

            exit
        fi
#signout
    elif [[ ${output} -eq 104 ]] ; then
        if [[ "${input}" == "" ]] ; then
        fi
    fi
done

makeid(){
echo "
                        ____ ___ ____ _   _   ___ _   _
                       / ___|_ _/ ___| \ | | |_ _| \ | |
                       \___ \| | |  _|  \| |  | ||  \| |
                        ___) | | |_| | |\  |  | || |\  |
                       |____/___\____|_| \_| |___|_| \_|


                                                                           "
echo "                 "^[[44m"                   "^[[0m"   "^[[44m"  Duplicate check  "^[[0m
echo ^[[0m"                                                                "
echo "                 "^[[44m"           PW           "^[[0m"            "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                         "^[[44m"  SIGN IN  "^[[0m"     "^[[41m"   EXIT   "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "

}
signout(){

echo "
                        ____ ___ ____ _   _    ___  _   _ _____
                       / ___|_ _/ ___| \ | |  / _ \| | | |_   _|
                       \___ \| | |  _|  \| | | | | | | | | | |
                        ___) | | |_| | |\  | | |_| | |_| | | |
                       |____/___\____|_| \_|  \___/ \___/  |_|


                                                                           "
echo "                               "^[[44m"           ID           "^[[0m"                     "
echo ^[[0m"                                                                "
echo "                               "^[[44m"           PW           "^[[0m"                     "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo "                            "^[[44m"   SIGN IN   "^[[0m"     "^[[41m"    EXIT    "^[[0m
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "
echo ^[[0m"                                                                "


}
