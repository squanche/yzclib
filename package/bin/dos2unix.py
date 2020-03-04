# coding=utf-8
import os

import sys

# windows shell脚本转unix
def file_name(file_dir):
    for root, dirs, files in os.walk(file_dir):
        # print(root)  # 当前目录路径
        # print(dirs)  # 当前路径下所有子目录
        # print(files)  # 当前路径下所有非目录子文件
        for file_name in files:
            if ".sh" in file_name:
                # print file_name
                # file_names.append(file_name)
                os.system("sed -i 's/\r//' %s" % file_name)
                print "当前目录下的%s文件dos2unix完成" % file_name

file_name(sys.path[0])
