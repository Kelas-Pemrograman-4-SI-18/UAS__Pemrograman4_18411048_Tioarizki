const baju = require ('../model/Baju.js')
const response = require('../config/response')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId

exports.inputDataBaju = (data, gambar) =>
    new Promise(async (resolve, reject)=> {

        const bajuBaru = new baju  ({
            kodeBaju: data.kodeBaju,
            stok: data.stok,
            harga: data.harga,
            jenisBaju: data.jenisBaju,
            gambar: gambar
        })

        await baju.findOne({kodeBaju: data.kodeBaju})
            .then(baju => {
                if (baju) {
                    reject(response.commonErrorMsg('Kode Baju Sudah digunakan'))
                }else {
                     bajuBaru.save()
                      .then(r=>{
                           resolve(response.commonSucsessMsg('Berhasil Menginput Data'))
                       }).catch(err => {
                          reject(response.commonErrorMsg('Mohon Maaf Input Baju Gagal'))
                    })
                }
            }).catch(err => {
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Keslahan Pada Server Kami'))
        })
    })

exports.lihatDataBaju = () =>
    new Promise(async (resolve, reject) => {
        await baju.find({})
            .then( result => {
                resolve(response.commonResult(result))
        })
            .catch(() => reject(response.commonErrorMsg('Mohon Maaf Terjadi Keslahan Pada Server Kami')))
    })

exports.lihatDetailDataBaju = (kodeBaju) =>
    new Promise(async (resolve, reject) => {
        await baju.findOne({kodeBaju: kodeBaju})
            .then( result => {
                resolve(response.commonResult(result))
            })
            .catch(() => reject(response.commonErrorMsg('Mohon Maaf Terjadi Keslahan Pada Server Kami')))
    })

exports.updateBaju = (id, data, gambar) =>
    new Promise(async  (resolve, reject) => {
       await baju.updateOne (
            {_id : ObjectId(id)},
            {
                $set: {
                    kodeBaju: data.kodeBaju,
                    stok: data.stok,
                    harga: data.harga,
                    jenisBaju: data.jenisBaju,
                    gambar: gambar
                }
            }
        ).then(baju => {
            resolve(response.commonSucsessMsg('Berhasil Mengubah Data'))
        }).catch(err => {
            reject(response.commonErrorMsg('Mohon Maaf Terjadi Keslahan Pada Server Kami'))
        })
    })

exports.hapusbaju = (_id) =>
    new Promise(async (resolve, reject)=>{
      await baju.remove({_id: ObjectId(_id)})
          .then(() =>{
              resolve(response.commonSucsessMsg('Berhasil Menghapus Data'))
          }).catch(() => {
              reject(response.commonErrorMsg('Mohon Maaf Terjadi Keslahan Pada Server Kami'))
          })
    })